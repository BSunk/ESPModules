package com.bsunk.esplight.settings;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SettingsContract.View {

    @BindView(R.id.device_recyclerview) RecyclerView deviceRecyclerView;

    SettingsPresenter mPresenter;
    DevicesAdapter devicesAdapter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mPresenter = new SettingsPresenter(this);
        ButterKnife.bind(this, rootView);
        mPresenter.getDeviceListFromRealm();
        return rootView;
    }

    @Override
    public void showDevices(List<LightModel> devices) {
        devicesAdapter = new DevicesAdapter(devices);
        deviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceRecyclerView.setAdapter(devicesAdapter);
    }

    public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {

        private List<LightModel> devices;

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @BindView(R.id.device_name) TextView deviceName;
            @BindView(R.id.device_id) TextView deviceID;

            public MyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.title_df)
                        .items(R.array.events)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                for(int i=0; i<text.length; i++) {
                                    if(text[0].equals("Incoming call")) {
                                        Timber.v("Incoming calls checked");
                                    }
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.ok_df)
                        .negativeText(R.string.dismiss_df)
                        .show();
            }
        }

        public DevicesAdapter(List<LightModel> deviceList){
            devices = deviceList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_device_listview_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            LightModel currentObject = devices.get(position);
            holder.deviceName.setText(currentObject.getName());
            holder.deviceID.setText(currentObject.getChipID());
        }

        @Override
        public int getItemCount() {
            return devices.size();
        }

    }

}
