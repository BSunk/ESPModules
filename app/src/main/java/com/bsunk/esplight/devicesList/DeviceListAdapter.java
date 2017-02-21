package com.bsunk.esplight.devicesList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

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

    private ClickListener mItemListener;
    private RealmResults<LightModel> mDeviceList;
    private Context mContext;

    DeviceListContract.Presenter mPresenter;

    DeviceListAdapter(
            Context context,
            RealmResults<LightModel> realmResults,
            boolean automaticUpdate,
            boolean animateIdType,
            String animateExtraColumnName,
            ClickListener itemListener,
            DeviceListContract.Presenter presenter) {
        super(context, realmResults, automaticUpdate, animateIdType, animateExtraColumnName);
        mItemListener = itemListener;
        mDeviceList = realmResults;
        mContext = context;
        mPresenter = presenter;
    }

    @Override
    public DeviceListAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View roverView = inflater.inflate(R.layout.device_item, parent, false);
        return new DeviceListAdapter.ViewHolder(roverView, mItemListener);
    }

    @Override
    public void onBindRealmViewHolder(DeviceListAdapter.ViewHolder viewHolder, final int position) {

        final LightModel lightModel = mDeviceList.get(position);

        double brightnessPercent = (lightModel.getBrightness()/255.0)*100;

        viewHolder.name.setText(lightModel.getName());
        viewHolder.seekbar.setProgress((int) brightnessPercent);
        viewHolder.pattern.setText(mContext.getString(R.string.pattern, lightModel.getPattern()));
        viewHolder.brightness.setText(Math.round(brightnessPercent)+"%");

        if(lightModel.getConnectionCheck()) {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_check_circle_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.green));
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_on));
            viewHolder.seekbar.setEnabled(true);
        }
        else {
            viewHolder.connection.setImageDrawable(mContext.getDrawable(R.drawable.ic_error_black_24dp));
            viewHolder.connection.setColorFilter(getContext().getResources().getColor(R.color.red));
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_off));
            viewHolder.seekbar.setEnabled(false);
        }

        if(lightModel.isPower()) {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_on));
        }
        else {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_off));
        }

        viewHolder.bulbIV.setOnClickListener((view -> {
            if(lightModel.isPower()) {
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
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    private LightModel getItem(int position) {
        return mDeviceList.get(position);
    }

    class ViewHolder extends RealmViewHolder implements View.OnClickListener {

        @BindView(R.id.brightness_seekbar) SeekBar seekbar;
        @BindView(R.id.imageView) ImageView bulbIV;
        @BindView(R.id.connection_iv) ImageView connection;
        @BindView(R.id.device_name) TextView name;
        @BindView(R.id.device_brightness) TextView brightness;
        @BindView(R.id.device_pattern) TextView pattern;
        private ClickListener mItemListener;

        ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            mItemListener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            LightModel lightModel = getItem(position);
            mItemListener.onDeviceClick(lightModel, v);
        }
    }
}

