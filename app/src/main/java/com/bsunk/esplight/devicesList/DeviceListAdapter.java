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

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Bharat on 2/20/2017.
 */

public class DeviceListAdapter extends RealmBasedRecyclerViewAdapter<LightModel, DeviceListAdapter.ViewHolder> {

    private ClickListener mItemListener;
    private RealmResults<LightModel> mDeviceList;
    private Context mContext;

    public DeviceListAdapter(
            Context context,
            RealmResults<LightModel> realmResults,
            boolean automaticUpdate,
            boolean animateIdType,
            String animateExtraColumnName,
            ClickListener itemListener) {
        super(context, realmResults, automaticUpdate, animateIdType, animateExtraColumnName);
        mItemListener = itemListener;
        mDeviceList = realmResults;
        mContext = context;
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
        double brightnessPercent = (mDeviceList.get(position).getBrightness()/255.0)*100;
        viewHolder.name.setText(mDeviceList.get(position).getName());
        viewHolder.seekbar.setProgress((int) brightnessPercent);

        viewHolder.brightness.setText(Math.round(brightnessPercent)+"%");

        if(mDeviceList.get(position).getConnectionCheck()) {
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

        if(mDeviceList.get(position).isPower()) {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_on));
        }
        else {
            viewHolder.bulbIV.setColorFilter(getContext().getResources().getColor(R.color.bulb_off));
        }

        viewHolder.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                mPresenter.setBrightness(mDeviceList.get(position).getIp(),
//                        mDeviceList.get(position).getPort(),
//                        seekBar.getProgress(),
//                        mDeviceList.get(position).getChipID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    public LightModel getItem(int position) {
        return mDeviceList.get(position);
    }

    class ViewHolder extends RealmViewHolder implements View.OnClickListener {

        TextView name;
        TextView brightness;
        SeekBar seekbar;
        ImageView bulbIV;
        ImageView connection;
        private ClickListener mItemListener;

        ViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            mItemListener = listener;
            name = (TextView) itemView.findViewById(R.id.device_name);
            brightness = (TextView) itemView.findViewById(R.id.device_brightness);
            seekbar = (SeekBar) itemView.findViewById(R.id.brightness_seekbar);
            bulbIV = (ImageView) itemView.findViewById(R.id.imageView);
            connection = (ImageView) itemView.findViewById(R.id.connection_iv);
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

