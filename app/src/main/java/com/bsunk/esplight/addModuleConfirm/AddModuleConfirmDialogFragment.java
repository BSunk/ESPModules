package com.bsunk.esplight.addModuleConfirm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

}
