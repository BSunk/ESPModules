package com.bsunk.esplight.devicesList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment implements DeviceListContract.View {

    @BindView(R.id.recycler_view_device_list)
    RecyclerView deviceListRV;

    DeviceListPresenter mPresenter;
    List<LightModel> mDeviceList;
    DeviceListAdapter mAdapter;

    public DeviceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_device_list, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter = new DeviceListPresenter(this);
        return rootView;
    }

    public void showDevices(RealmResults<LightModel> lightModels) {
        mDeviceList = lightModels;
        mAdapter=null;
        mAdapter = new DeviceListAdapter(mClickListener);
        deviceListRV.setAdapter(mAdapter);
        deviceListRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    ClickListener mClickListener = new ClickListener() {
        @Override
        public void onDeviceClick(LightModel clickedDevice, View v) {
            Timber.v("Clicked");
        }
    };


    public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListFragment.DeviceListAdapter.ViewHolder> {

        private DeviceListFragment.ClickListener mItemListener;

        public DeviceListAdapter(DeviceListFragment.ClickListener itemListener) {
            mItemListener = itemListener;
        }

        @Override
        public DeviceListFragment.DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View roverView = inflater.inflate(R.layout.device_item, parent, false);
            // Return a new holder instance
            DeviceListFragment.DeviceListAdapter.ViewHolder viewHolder = new DeviceListFragment.DeviceListAdapter.ViewHolder(roverView, mItemListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DeviceListFragment.DeviceListAdapter.ViewHolder viewHolder, int position) {
           viewHolder.name.setText(mDeviceList.get(position).getName());
        }


        @Override
        public int getItemCount() {
            return mDeviceList.size();
        }

        public LightModel getItem(int position) {
            return mDeviceList.get(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView name;
            private DeviceListFragment.ClickListener mItemListener;

            ViewHolder(View itemView, DeviceListFragment.ClickListener listener) {
                super(itemView);
                mItemListener = listener;
                name = (TextView) itemView.findViewById(R.id.device_name);
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


