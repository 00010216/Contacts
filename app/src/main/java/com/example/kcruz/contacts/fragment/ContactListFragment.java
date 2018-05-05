package com.example.kcruz.contacts.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.adapter.ContactListAdapter;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment implements ContactListAdapter.ContactListClickListener {
    public static final String ARG_ITEM_ID = "contact_list";

    Activity activity;
    RecyclerView contactListView;
    List<Contact> contacts;
    ContactListAdapter contactListAdapter;
    LinearLayoutManager lManager;

    SharedPreference sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list,container,false);
        findViewsById(view);
        contactListView.setHasFixedSize(true);

        lManager = new LinearLayoutManager(container.getContext());
        contactListView.setLayoutManager(lManager);

        setContacts();

        contactListAdapter = new ContactListAdapter(activity, contacts, this);
        contactListView.setAdapter(contactListAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setContacts(){
        Contact contact1 = new Contact(1, "Karla", "Cruz", "78890067");
        Contact contact2 = new Contact(2, "Harry", "Styles", "78890067");
        Contact contact3 = new Contact(3, "Louis", "Tomlinson", "78890067");
        Contact contact4 = new Contact(4, "Niall", "Horan", "78890067");
        Contact contact5 = new Contact(5, "Liam", "Payne", "78890067");
        Contact contact6 = new Contact(6, "Zayn", "Malik", "78890067");
        Contact contact7 = new Contact(7, "Shawn", "Mendes", "78890067");
        Contact contact8 = new Contact(8, "Jude", "Law", "78890067");
        Contact contact9 = new Contact(9, "Eddie", "Redmayne", "78890067");
        Contact contact10 = new Contact(10, "Chris", "Hemsworth", "78890067");
        Contact contact11 = new Contact(11, "Chris", "Evans", "78890067");

        contacts = new ArrayList<Contact>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        contacts.add(contact5);
        contacts.add(contact6);
        contacts.add(contact7);
        contacts.add(contact8);
        contacts.add(contact9);
        contacts.add(contact10);
        contacts.add(contact11);

        System.out.println("Los productos fueron agregados");
    }

    private void findViewsById(View view) {
        contactListView = (RecyclerView) view.findViewById(R.id.list_contact);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        super.onResume();
    }

    @Override
    public void onContactClick(View v, int position) {
        System.out.println("POSITION OBTENIDA"+position);
        Contact c = contacts.get(position);
        Toast.makeText(activity, c.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onContactLongClick(View v, int position) {
        ImageView button = (ImageView) v.findViewById(R.id.imgbtn_favorite);

        String tag = button.getTag().toString();
        if(tag.equalsIgnoreCase("grey")){
            sharedPreference.addFavorite(activity, contacts.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.add_favr),
                    Toast.LENGTH_SHORT).show();

            button.setTag("red");
            button.setImageResource(R.drawable.ic_fav);
        } else {
            sharedPreference.removeFavorite(activity, contacts.get(position));
            button.setTag("grey");
            button.setImageResource(R.drawable.ic_fav_border);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.remove_favr),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
