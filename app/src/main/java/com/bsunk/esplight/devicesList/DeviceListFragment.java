package com.bsunk.esplight.devicesList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment implements DeviceListContract.View {

    @BindView(R.id.recycler_view_device_list)
    RealmRecyclerView deviceListRV;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    DeviceListContract.Presenter mPresenter;

    DeviceListAdapter mAdapter;

    public DeviceListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter = new DeviceListPresenter(this);
        mPresenter.getDevices();
        return rootView;
    }

    public void showDevices(RealmResults<LightModel> lightModels) {
        mAdapter = new DeviceListAdapter(
                getContext(),
                lightModels,
                true,
                false,
                null,
                mPresenter);

        deviceListRV.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(()-> {
            swipeRefreshLayout.setRefreshing(true);
            mPresenter.updateData();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

}


