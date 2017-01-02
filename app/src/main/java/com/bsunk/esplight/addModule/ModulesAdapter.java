package com.bsunk.esplight.addModule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bsunk.esplight.R;
import com.bsunk.esplight.data.model.mDNSModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bharat on 12/13/2016.
 */

public class ModulesAdapter extends RecyclerView.Adapter<ModulesAdapter.MyViewHolder> {

    List<mDNSModule> mList = new ArrayList<>();
    OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView ip, port, name;

        public MyViewHolder(View view) {
            super(view);
            ip = (TextView) view.findViewById(R.id.module_ip);
            name = (TextView) view.findViewById(R.id.module_name);
            port = (TextView) view.findViewById(R.id.module_port);
        }

        public void bind(final mDNSModule module, final OnItemClickListener listener) {
            ip.setText(module.getIp());
            name.setText(module.getName());
            port.setText(String.valueOf(module.getPort()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(module);
                }
            });
        }
    }

    public ModulesAdapter(List<mDNSModule> mList, OnItemClickListener listener) {
        this.mList = mList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_modules_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(mDNSModule module);
    }

}