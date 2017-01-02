package com.bsunk.esplight.addModuleConfirm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bsunk.esplight.R;

public class AddModuleConfirmDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module_confirm_dialog);
        setTitle(getString(R.string.confirm_title));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.confirm_activity_container, new AddModuleConfirmDialogFragment())
                .commit();

    }
}
