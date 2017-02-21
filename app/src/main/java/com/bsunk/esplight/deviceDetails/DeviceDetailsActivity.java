package com.bsunk.esplight.deviceDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.LightModel;

import butterknife.BindView;
import io.realm.Realm;

public class DeviceDetailsActivity extends AppCompatActivity implements DeviceDetailsContract.View {


    DeviceDetailsPresenter mPresenter;
    LightModel currentDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DeviceDetailsPresenter(this);
        setContentView(R.layout.activity_device_details);
        String chipID = getIntent().getStringExtra("chipID");
        if(chipID!=null) {
            mPresenter.getRealmObject(chipID);
        }

    }

    public void setCurrentDeviceObject(LightModel lightModel) {
        currentDevice = lightModel;
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}
