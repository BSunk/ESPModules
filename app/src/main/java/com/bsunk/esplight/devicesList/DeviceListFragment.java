package com.bsunk.esplight.devicesList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.components.DaggerDeviceListViewComponent;
import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.esplight.data.modules.DeviceListViewModule;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment implements DeviceListContract.View {

    @BindView(R.id.recycler_view_device_list)
    RealmRecyclerView deviceListRV;

    @Inject
    DeviceListPresenter mPresenter;

    DeviceListAdapter mAdapter;

    public DeviceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        DaggerDeviceListViewComponent.builder().deviceListViewModule(new DeviceListViewModule(this))
                .build().inject(this);
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

}


