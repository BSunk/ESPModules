package com.bsunk.esplight.addModuleConfirm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.components.DaggerAddModuleConfirmViewComponent;
import com.bsunk.esplight.data.model.mDNSModule;
import com.bsunk.esplight.data.modules.AddModuleConfirmViewModule;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddModuleConfirmDialogFragment extends Fragment implements AddModulesConfirmContract.View {

    @Inject
    AddModulesConfirmPresenter mPresenter;

    @BindView(R.id.input_name)
    EditText displayName;
    @BindView(R.id.input_chipid)
    EditText chipID;
    @BindView(R.id.input_ip)
    EditText IPAddress;
    @BindView(R.id.input_port)
    EditText port;
    @BindView(R.id.validationImageView)
    ImageView validationImageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public AddModuleConfirmDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_module_confirm_dialog, container, false);
        ButterKnife.bind(this, rootView);
        DaggerAddModuleConfirmViewComponent.builder()
                .addModuleConfirmViewModule(new AddModuleConfirmViewModule(this))
                .build().inject(this);

        if(getActivity().getIntent().getParcelableExtra("module")!=null) {
            mDNSModule module = getActivity().getIntent().getParcelableExtra("module");
            mPresenter.onItemClickModule(module);
        }

        setName(getResources().getString(R.string.display_name_default));
        return rootView;
    }

    public void setValues(mDNSModule module) {
        chipID.setText(module.getName());
        IPAddress.setText(module.getIp().substring(1,module.getIp().length()));
        port.setText(String.valueOf(module.getPort()));
    }

    public void setName(String name) {
        displayName.setText(name);
    }

    @OnClick(R.id.buttonTest)
    public void buttonTestOnClick() {
        mPresenter.testConnection(IPAddress.getText().toString(), Integer.parseInt(port.getText().toString()));
    }

    public void showValidation(boolean show) {
        validationImageView.setVisibility(View.VISIBLE);
        if(show) {
            validationImageView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_check_black_24dp));
            validationImageView.setColorFilter(ContextCompat.getColor(getContext(),R.color.green));
        }
        else {
            validationImageView.setImageDrawable(getActivity().getDrawable(R.drawable.ic_cancel_black_24dp));
            validationImageView.setColorFilter(ContextCompat.getColor(getContext(),R.color.red));
        }
    }

    public void showProgressBar(boolean show) {
        if(show) { progressBar.setVisibility(View.VISIBLE);}
        else {progressBar.setVisibility(View.GONE);}
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

}
