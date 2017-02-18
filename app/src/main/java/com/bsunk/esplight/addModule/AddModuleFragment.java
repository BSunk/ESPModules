package com.bsunk.esplight.addModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bsunk.esplight.App;
import com.bsunk.esplight.R;
import com.bsunk.esplight.addModuleConfirm.AddModuleConfirmDialogActivity;
import com.bsunk.esplight.data.components.DaggerAddModuleViewComponent;
import com.bsunk.esplight.data.model.mDNSModule;
import com.bsunk.esplight.data.modules.AddModuleViewModule;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class AddModuleFragment extends Fragment implements AddModulesContract.View, ModulesAdapter.OnItemClickListener{

    private List<mDNSModule> modulesList = new ArrayList<>();
    private ModulesAdapter modulesAdapter;
    static final int ADD_CONNECTION_REQUEST = 1;

    @Inject
    AddModulesPresenter mPresenter;

    @BindView(R.id.add_modules_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.searchingLayout)
    LinearLayout searchingLinearLayout;
    @BindView(R.id.emptyMessageLayout)
    LinearLayout emptyMessageLinearLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    public AddModuleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_module, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        DaggerAddModuleViewComponent.builder()
                .nsdComponent(((App) getActivity().getApplicationContext()).getNsdComponent())
                .addModuleViewModule(new AddModuleViewModule(this))
                .build().inject(this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_module_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                mPresenter.startDiscoveryTimeOut();
                return true;
            case R.id.add:
                Intent intent = new Intent(getActivity(), AddModuleConfirmDialogActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, ADD_CONNECTION_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_CONNECTION_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Added connection successfully", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addModuleToList(mDNSModule module) {
        modulesList.add(module);
        modulesAdapter.notifyItemInserted(modulesList.size()-1);
    }

    public void resetList() {
        modulesList.clear();
        modulesAdapter.notifyDataSetChanged();
    }

    public void initializeList() {
        modulesAdapter = new ModulesAdapter(modulesList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(modulesAdapter);
    }

    public void startSearchAnimation(boolean isTrue) {
        if(isTrue) {
            searchingLinearLayout.setVisibility(View.VISIBLE);
            avi.smoothToShow();
        }
        else {
            searchingLinearLayout.setVisibility(View.GONE);
            avi.smoothToHide();
        }
    }

    public void showEmptyMessage(boolean isTrue) {
        if(isTrue && modulesList.isEmpty()) { emptyMessageLinearLayout.setVisibility(View.VISIBLE); }
        else { emptyMessageLinearLayout.setVisibility(View.GONE);}
    }

    @OnClick(R.id.refresh_button)
    public void refreshButtonOnClick() {
        mPresenter.startDiscoveryTimeOut();
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
        modulesList.clear();
        if(modulesAdapter!=null) {
        modulesAdapter.notifyDataSetChanged(); }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    public void onItemClick(mDNSModule module) {
        Intent intent = new Intent(getActivity(), AddModuleConfirmDialogActivity.class);
        intent.putExtra("module", module);
        //startActivity(intent);
        startActivityForResult(intent, ADD_CONNECTION_REQUEST);
    }

}
