package com.example.kcruz.contacts.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.adapter.ContactDataListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDataListFragment extends Fragment {
    List<String> contactData;
    Activity activity;
    RecyclerView contactDataListView;
    ContactDataListAdapter contactDataListAdapter;
    LinearLayoutManager lManager;
    ContactDataListListener mCallback;

    public interface ContactDataListListener{
        public List<String> getContactData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_data, container,
                false);

        Bundle args = getArguments();
        if (args != null ) {
            if (args.getInt("option") == 1) contactData = new ArrayList<>();
            else contactData = mCallback.getContactData(); //asignamos la lista que se obtuvo del activity a la lista
            //que se enviara al adapter
        }

        findViewsById(view);
        contactDataListView.setHasFixedSize(true);

        lManager = new LinearLayoutManager(container.getContext());
        contactDataListView.setLayoutManager(lManager);

        //se le envia la info con la list al adapter
        if (contactData != null) {
            contactDataListAdapter = new ContactDataListAdapter(activity, contactData);
            contactDataListView.setAdapter(contactDataListAdapter);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findViewsById(View view) {
        //usa la view para encontrar los id desde el recycler ya que el recycler contiene los item
        contactDataListView = (RecyclerView) view.findViewById(R.id.data_list_contact);
    }

}
