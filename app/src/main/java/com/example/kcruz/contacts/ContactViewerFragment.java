package com.example.kcruz.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ContactViewerFragment extends Fragment {
    TextView firstName, lastName, number,contactName;
    ImageView contactImage; //declaracion de variables

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_viewer, container, false);

        //define la vista respectiva al componente
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        number = view.findViewById(R.id.number);
        contactImage = view.findViewById(R.id.contact_image);
        contactName = view.findViewById(R.id.contact_name);
        Bundle bundle = this.getArguments();
        Contact contact;

        if(bundle != null){
            contact = bundle.getParcelable("KEY"); //identifica el bundle que contiene el objeto
            Toast.makeText(getActivity(), "Item: " + bundle.getString("KEY"), Toast.LENGTH_SHORT).show();

            //coloca la info guardada en el objeto y la coloca en el view respectivo
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            number.setText(contact.getNumber());
            contactImage.setImageResource(contact.getImage());
            contactName.setText(contact.contactName());
        }

        return view;
    }
}