package com.example.hp.traveloh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 26-02-2017.
 */

public class PeopleDescriptionAdapter extends RecyclerView.Adapter<PeopleDescriptionAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public PeopleDescriptionAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public PeopleDescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleDescriptionAdapter.ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);
        holder.usernames.setText(listItem.getmName());
        holder.phonenumbers.setText(listItem.getmNumber());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView usernames;
        public TextView phonenumbers;
        public ViewHolder(View itemView) {
            super(itemView);
            usernames = (TextView) itemView.findViewById(R.id.traveller_name_cardView);
            phonenumbers = (TextView) itemView.findViewById(R.id.traveller_phone_number_cardView);
        }
    }
}
