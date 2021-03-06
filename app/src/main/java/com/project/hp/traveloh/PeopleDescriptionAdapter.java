package com.project.hp.traveloh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                .inflate(com.project.hp.traveloh.R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleDescriptionAdapter.ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);
        holder.usernames.setText(listItem.getmName());
        holder.phonenumbers.setText(listItem.getmNumber());
        holder.froms.setText(listItem.getmFrom());
        holder.tos.setText(listItem.getmTo());
        holder.dates.setText(listItem.getmDate());
        holder.times.setText(listItem.getmTime());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You Clicked"+" "+listItem.getmName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView usernames;
        public TextView phonenumbers;
        public TextView froms,tos,dates,times;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(com.project.hp.traveloh.R.id.relative_layout_people_around);
            usernames = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_name_cardView);
            phonenumbers = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_phone_number_cardView);
            froms = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_from_cardView);
            tos = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_to_cardView);
            dates = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_date_cardView);
            times = (TextView) itemView.findViewById(com.project.hp.traveloh.R.id.traveller_time_cardView);
        }
    }
}
