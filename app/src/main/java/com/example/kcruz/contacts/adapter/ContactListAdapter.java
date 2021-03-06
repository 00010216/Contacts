package com.example.kcruz.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.utils.SharedPreference;

import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {
    private Context mContext;
    List<Contact> mContacts;
    SharedPreference sharedPreference;

    public interface ContactListClickListener{
        public void onContactClick(View v, int position);
        public void onContactLongClick(View v, int position);

    }
    private ContactListClickListener mListener;

    public ContactListAdapter(Context context, List<Contact> contacts, ContactListClickListener mListener){
        mContext = context;
        mContacts = contacts;
        this.mListener = mListener;
        sharedPreference = new SharedPreference();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rel;
        TextView contactNameTxt;
        ImageView favoriteImg;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.pdt_layout_item);
            contactNameTxt = itemView.findViewById(R.id.txt_pdt_name);
            favoriteImg = itemView.findViewById(R.id.imgbtn_favorite);
        }
    }

    @Override
    public ContactsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return (new ContactsViewHolder(v));
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        final Contact contact = (Contact) mContacts.get(position);
        holder.contactNameTxt.setText(contact.getContactName());

        if (checkFavoriteItem(contact)) {
            holder.favoriteImg.setImageResource(R.drawable.ic_fav);
            holder.favoriteImg.setTag("red");
        } else {
            holder.favoriteImg.setImageResource(R.drawable.ic_fav_border);
            holder.favoriteImg.setTag("grey");
        }

        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onContactClick(v, position);
            }
        });

        holder.favoriteImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onContactLongClick(v, position);
                return true;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    public boolean checkFavoriteItem(Contact checkContact) {
        boolean check = false;
        List<Contact> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            for (Contact c : favorites) {
                if (c.equals(checkContact)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }



    /*@Override
    public void add(Product product) {
        super.add(product);
        mProducts.add(product);
        notifyDataSetChanged();
    }
*/

    public void remove(Contact product) {
        //super.remove(product);
        mContacts.remove(product);
        notifyDataSetChanged();
    }
}
