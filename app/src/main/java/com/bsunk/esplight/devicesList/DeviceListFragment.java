package com.bsunk.esplight.devicesList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment implements DeviceListContract.View {

    @BindView(R.id.recycler_view_device_list)
    RealmRecyclerView deviceListRV;

    DeviceListPresenter mPresenter;
    DeviceListAdapter mAdapter;

    public DeviceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter = new DeviceListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_device_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void showDevices(RealmResults<LightModel> lightModels) {
        mAdapter = new DeviceListAdapter(
                getContext(),
                lightModels,
                true,
                true,
                null, mClickListener);

        deviceListRV.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    public void updatedData() {
        if (mAdapter!=null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    ClickListener mClickListener = new ClickListener() {
        @Override
        public void onDeviceClick(LightModel clickedDevice, View v) {
            Timber.v("Clicked");
        }
    };


    public class DeviceListAdapter extends RealmBasedRecyclerViewAdapter<LightModel,
            DeviceListAdapter.ViewHolder>  {

        private DeviceListFragment.ClickListener mItemListener;
        private RealmResults<LightModel> mDeviceList;

        public DeviceListAdapter(
                Context context,
                RealmResults<LightModel> realmResults,
                boolean automaticUpdate,
                boolean animateIdType,
                String animateExtraColumnName,
                DeviceListFragment.ClickListener itemListener) {
            super(context, realmResults, automaticUpdate, animateIdType, animateExtraColumnName);
            mItemListener = itemListener;
            mDeviceList = realmResults;
        }

        @Override
        public DeviceListFragment.DeviceListAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View roverView = inflater.inflate(R.layout.device_item, parent, false);
            return new DeviceListFragment.DeviceListAdapter.ViewHolder(roverView, mItemListener);
        }

        @Override
        public void onBindRealmViewHolder(DeviceListFragment.DeviceListAdapter.ViewHolder viewHolder, int position) {
            double brightnessPercent = (mDeviceList.get(position).getBrightness()/255.0)*100;
            viewHolder.name.setText(mDeviceList.get(position).getName());
            viewHolder.brightness.setText(Math.round(brightnessPercent)+"%");
            if(mDeviceList.get(position).isPower()) {
                viewHolder.bulbIV.setColorFilter(getContext().getColor(R.color.bulb_on));
                viewHolder.seekbar.setEnabled(true);
                viewHolder.seekbar.setProgress((int) brightnessPercent);
            }
            else {
                viewHolder.bulbIV.setColorFilter(getContext().getColor(R.color.bulb_off));
                viewHolder.seekbar.setEnabled(false);
            }
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
            private DeviceListFragment.ClickListener mItemListener;

            ViewHolder(View itemView, DeviceListFragment.ClickListener listener) {
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

    public interface ClickListener {
        void onDeviceClick(LightModel clickedDevice, View v);
    }
}


