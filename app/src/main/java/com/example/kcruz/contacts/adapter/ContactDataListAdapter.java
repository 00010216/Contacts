package com.example.kcruz.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.beans.Contact;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContactDataListAdapter extends RecyclerView.Adapter<ContactDataListAdapter.DataViewHolder>{
    private Context mContext;
    List<String> contactData;

    public ContactDataListAdapter(Context mContext, List< String> contactData) {
        this.mContext = mContext;
        this.contactData = contactData;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rel;
        TextView informationDescription, contactData;

        public DataViewHolder(View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.information_layout_item);
            informationDescription = itemView.findViewById(R.id.information_description);
            contactData = itemView.findViewById(R.id.contact_data);
        }
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_data_item, parent, false);
        return (new DataViewHolder(view));
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        //Lo que mostrara en la vista que se recicle
       final String data = contactData.get(position);

       switch(position){
           case 0:
               //Id de contact
               break;
           case 3: case 4: case 5:
               //numero
             holder.informationDescription.setText(contactData.get(position));
             break;
           case 6:
               //Email
               holder.informationDescription.setText(contactData.get(position));
               break;
           default:
               break;
       }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }
}
