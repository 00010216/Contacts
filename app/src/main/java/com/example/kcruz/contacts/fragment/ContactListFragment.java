package com.example.kcruz.contacts.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.activity.ContactInformationActivity;
import com.example.kcruz.contacts.adapter.ContactListAdapter;
import com.example.kcruz.contacts.beans.Communicator;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment implements ContactListAdapter.ContactListClickListener{

    public static final String ARG_ITEM_ID = "contact_list";

    Activity activity;
    RecyclerView contactListView;
    List<Contact> contacts;
    ContactListAdapter contactListAdapter;
    LinearLayoutManager lManager;
    Communicator mCallback; //instanceando interfaz

    SharedPreference sharedPreference;

    public ContactListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Communicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container,
                false);

        contacts = mCallback.importContacts(); //llamando metodo de importar contactos desde el main
        contacts = new ArrayList<>();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setContacts() {

        Contact contact1 = new Contact(1,"Karla","Cruz","77663456","00010216@uca.edu.sv","21/09/97");
        Contact contact2 = new Contact(2,"Lucia","Romualdo","67894567","123432@gmail.com","24/05/18");
        Contact contact3 = new Contact(3,"Pedro","Ortiz","78908890","12345678","78900000");
        Contact contact4 = new Contact(4,"Marta","Zavala","22345678","12345678@gmail.com","24/12/12");

        contacts = new ArrayList<Contact>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        System.out.println("Los productos fueron agregados");
    }

    private void findViewsById(View view) {
        contactListView = (RecyclerView) view.findViewById(R.id.list_contact);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        //getActivity().getSupportActionBar().setTitle(R.string.app_name);
        super.onResume();
    }

    @Override
    public void onContactClick(View v, int position) {
        System.out.println("POSITION OBTENIDA"+position);
        Contact contact = new Contact(contacts.get(position).getId(), contacts.get(position).getFirstName(),
                contacts.get(position).getLastName(),contacts.get(position).getNumber1(),contacts.get(position).getEmail(),contacts.get(position).getBirthday());

        Bundle bundle = new Bundle(); //procesa la info que se enviara a traves del intent
        bundle.putParcelable("KEY", contact); //manda identificador de bundle

        if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            Toast.makeText(activity, contact.toString(), Toast.LENGTH_LONG).show();
            ContactViewerFragment frag = new ContactViewerFragment();
            frag.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.viewer, frag);
            fragmentTransaction.commit();

        } else {
            Toast.makeText(activity, contact.toString(), Toast.LENGTH_LONG).show();
            Intent newIntent = new Intent(getActivity().getApplicationContext(), ContactInformationActivity.class);
            newIntent.setAction(Intent.ACTION_SEND);
            newIntent.putExtras(bundle);
            startActivity(newIntent);
        }

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

    public void addContact(Contact contact) {
        contacts.add(contact);
        contactListAdapter.notifyItemInserted(contacts.size()-1);
    }
}

