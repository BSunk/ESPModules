package com.bsunk.esplight.devicesList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.makeglow.MakeGlow;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Bharat on 2/20/2017.
 */

public class DeviceListAdapter extends RealmBasedRecyclerViewAdapter<LightModel, DeviceListAdapter.ViewHolder> {

    private RealmResults<LightModel> mDeviceList;
    private Context mContext;

    DeviceListContract.Presenter mPresenter;

    DeviceListAdapter(
            Context context,
            RealmResults<LightModel> realmResults,
            boolean automaticUpdate,
            boolean animateIdType,
            String animateExtraColumnName,
            DeviceListContract.Presenter presenter) {
        super(context, realmResults, automaticUpdate, animateIdType, animateExtraColumnName);
        mDeviceList = realmResults;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public DeviceListAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.device_item, parent, false);
        return new DeviceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindRealmViewHolder(DeviceListAdapter.ViewHolder viewHolder, final int position) {

        final LightModel lightModel = mDeviceList.get(position);

        double brightnessPercent = (lightModel.getBrightness()/255.0)*100;
        String hex = String.format("#%02x%02x%02x", lightModel.getSolidColorR(), lightModel.getSolidColorG(), lightModel.getSolidColorB());

        viewHolder.name.setText(lightModel.getName());
        viewHolder.seekbar.setProgress((int) brightnessPercent);
        viewHolder.brightness.setText(Math.round(brightnessPercent)+"%");

        if(lightModel.getConnectionCheck()) {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_check_circle_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.green));
            viewHolder.lightBulbView.setGlowOff(false);
            viewHolder.seekbar.setEnabled(true);
            viewHolder.patternSpinner.setEnabled(true);
        }
        else {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_error_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.red));
            viewHolder.lightBulbView.setGlowOff(true);
            viewHolder.seekbar.setEnabled(false);
            viewHolder.patternSpinner.setEnabled(false);
        }

        if(lightModel.getPower()) {
            viewHolder.lightBulbView.setGlowOff(false);
            setGlow(viewHolder, (int) brightnessPercent);
        }
        else {
            viewHolder.lightBulbView.setGlowOff(true);
        }

        viewHolder.lightBulbView.setOnClickListener((view -> {
            if(lightModel.getPower()) {
                mPresenter.setPower(lightModel.getIp(),
                        lightModel.getPort(),
                        0, lightModel.getChipID());
            }
            else {
                mPresenter.setPower(lightModel.getIp(),
                        lightModel.getPort(),
                        1, lightModel.getChipID());
            }
        }));

        viewHolder.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int brightness = (int) ((seekBar.getProgress()/100.0) *255);
                mPresenter.setBrightness(lightModel.getIp(),
                        lightModel.getPort(),
                        brightness, lightModel.getChipID());
                setGlow(viewHolder, brightness);
            }
        });

        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> patternList = new Gson().fromJson(lightModel.getPatternList(), listType);
        if(patternList!=null) {
            ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item, patternList);
            patternAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            viewHolder.patternSpinner.setAdapter(patternAdapter);
        }

        viewHolder.patternSpinner.setSelection(lightModel.getPattern());

        if(viewHolder.patternSpinner.getSelectedItem()!=null) {
            if (viewHolder.patternSpinner.getSelectedItem().toString().equals("Solid Color")) {
                viewHolder.colorPickerButton.setVisibility(View.VISIBLE);
                viewHolder.lightBulbView.setGlowColor(Color.parseColor(hex));

            } else {
                viewHolder.colorPickerButton.setVisibility(View.GONE);
                viewHolder.lightBulbView.setGlowColor(getContext().getColor(R.color.bulb_on));
            }
        }

        viewHolder.colorPickerButton.setBackgroundColor(Color.parseColor(hex));

        viewHolder.patternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(lightModel.getPattern()!=i) {
                    mPresenter.setPattern(lightModel.getIp(),
                            lightModel.getPort(),
                            i,
                            lightModel.getChipID());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        viewHolder.colorPickerButton.setOnClickListener(view ->
            ColorPickerDialogBuilder
                    .with(mContext)
                    .setTitle("Choose color")
                    .initialColor(Color.parseColor(hex))
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(8)
                    .setOnColorSelectedListener(selectedColor -> {
                            int r = Color.red(selectedColor);
                            int g = Color.green(selectedColor);
                            int b = Color.blue(selectedColor);
                            mPresenter.setSolidColor(lightModel.getIp(),
                                    lightModel.getPort(),
                                    r, g, b,
                                    lightModel.getChipID());
                    })
                    .setPositiveButton("ok", (dialogInterface, i, integers) ->  {})
                    .lightnessSliderOnly()
                    .build()
                    .show()
        );

    }

    private void setGlow(DeviceListAdapter.ViewHolder viewHolder, int brightness) {
            viewHolder.lightBulbView.setGlowOff(false);
            if(brightness>=0 && brightness<=20) {
                viewHolder.lightBulbView.setGlowRadius(3);
            }
            else if(brightness>=21 && brightness<=50) {
                viewHolder.lightBulbView.setGlowRadius(10);
            }
            else if(brightness>=51 && brightness<=75) {
                viewHolder.lightBulbView.setGlowRadius(18);
            }
            else {
                viewHolder.lightBulbView.setGlowRadius(25);
            }
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    class ViewHolder extends RealmViewHolder {
        @BindView(R.id.glowView)
        MakeGlow lightBulbView;
        @BindView(R.id.brightness_seekbar) SeekBar seekbar;
        @BindView(R.id.connection_iv) ImageView connection;
        @BindView(R.id.device_name) TextView name;
        @BindView(R.id.device_brightness) TextView brightness;
        @BindView(R.id.device_pattern_spinner) Spinner patternSpinner;
        @BindView(R.id.device_color_picker) ImageView colorPickerButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

