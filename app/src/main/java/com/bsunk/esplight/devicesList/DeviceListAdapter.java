package com.bsunk.esplight.devicesList;

import android.content.Context;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import timber.log.Timber;

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

        viewHolder.name.setText(lightModel.getName());
        viewHolder.seekbar.setProgress((int) brightnessPercent);
        viewHolder.brightness.setText(Math.round(brightnessPercent)+"%");

        if(lightModel.getConnectionCheck()) {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_check_circle_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.green));
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_on));
            viewHolder.seekbar.setEnabled(true);
            viewHolder.patternSpinner.setEnabled(true);
        }
        else {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_error_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.red));
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_off));
            viewHolder.seekbar.setEnabled(false);
            viewHolder.patternSpinner.setEnabled(false);
        }

        if(lightModel.getPower()) {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_on));
        }
        else {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_off));
        }

        viewHolder.bulbIV.setOnClickListener((view -> {
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
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    class ViewHolder extends RealmViewHolder {

        @BindView(R.id.brightness_seekbar) SeekBar seekbar;
        @BindView(R.id.imageView) ImageView bulbIV;
        @BindView(R.id.connection_iv) ImageView connection;
        @BindView(R.id.device_name) TextView name;
        @BindView(R.id.device_brightness) TextView brightness;
        @BindView(R.id.device_pattern_spinner) Spinner patternSpinner;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

