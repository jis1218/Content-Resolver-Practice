package com.example.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-09-26.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
    Context context = null;
    ArrayList<Contact> list = null;

    public RecyclerAdapter(Context context, ArrayList<Contact> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = list.get(position);
        //holder.getTvID().setText(contact.getId());
        holder.getTvName().setText(contact.getName());
        holder.getTvNumber().setText(contact.getNumber());
    }
}

class ViewHolder extends RecyclerView.ViewHolder{

    private TextView tvID, tvName, tvNumber;

    public ViewHolder(View itemView) {
        super(itemView);

        tvID = (TextView) itemView.findViewById(R.id.tvID);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvNumber = (TextView) itemView.findViewById(R.id.tvNumber);
    }


    public TextView getTvID() {
        return tvID;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvNumber() {
        return tvNumber;
    }
}
