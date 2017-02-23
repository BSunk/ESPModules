package com.bsunk.esplight;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.bsunk.esplight.addModule.AddModuleFragment;
import com.bsunk.esplight.devicesList.DeviceListFragment;
import com.bsunk.esplight.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    int selected=0;

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState!=null) {
            selected = savedInstanceState.getInt("selection");
            goToSelectedFragment(selected, true);
        }
        else {
            getSupportActionBar().setTitle(getString(R.string.devices_tab));
            DeviceListFragment deviceListFragment = new DeviceListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, deviceListFragment).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item ->  {
                switch (item.getItemId()) {
                    case R.id.action_devices:
                        selected=0;
                        break;
                    case R.id.action_add:
                        selected=1;
                        break;
                    case R.id.action_settings:
                        selected=2;
                        break;
                }
            goToSelectedFragment(selected, false);
            return true;
        });

    }

    private void goToSelectedFragment(int selection, boolean configChange) {
        Fragment fragment;
        int startRadius = 0;
        int endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
        Animator anim;
        int x,y;
        switch (selection) {
            case 0:
                if(!configChange) {
                    x = bottomNavigationView.getLeft();
                    y = bottomNavigationView.getBottom();
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                fragment = new DeviceListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                getSupportActionBar().setTitle(getString(R.string.devices_tab));
                break;
            case 1:
                if(!configChange) {
                    x = (bottomNavigationView.getRight() - bottomNavigationView.getLeft()) / 2;
                    y = bottomNavigationView.getBottom();
                    startRadius = 0;
                    endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                fragment = new AddModuleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                getSupportActionBar().setTitle(getString(R.string.add_device_tab));
                break;
            case 2:
                if(!configChange) {
                    x = bottomNavigationView.getRight();
                    y = bottomNavigationView.getBottom();
                    startRadius = 0;
                    endRadius = (int) Math.hypot(coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                    anim = ViewAnimationUtils.createCircularReveal(coordinatorLayout, x, y, startRadius, endRadius);
                    anim.start();
                }
                fragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
                getSupportActionBar().setTitle(getString(R.string.settings_tab));
                break;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("selection", selected);
    }

}
