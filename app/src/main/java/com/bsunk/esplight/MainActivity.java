package com.bsunk.esplight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bsunk.esplight.addModule.AddModuleFragment;
import com.bsunk.esplight.devicesList.DeviceListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.devices_tab));

        DeviceListFragment deviceListFragment = new DeviceListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, deviceListFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item ->  {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_devices:
                        fragment = new DeviceListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                        getSupportActionBar().setTitle(getString(R.string.devices_tab));
                        break;
                    case R.id.action_add:
                        fragment = new AddModuleFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                        getSupportActionBar().setTitle(getString(R.string.add_device_tab));
                        break;
                    case R.id.action_settings:
                        getSupportActionBar().setTitle(getString(R.string.settings_tab));
                        break;
                }
                return true;
        });

    }



}
