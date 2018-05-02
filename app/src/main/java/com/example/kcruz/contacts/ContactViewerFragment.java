package com.example.kcruz.contacts;

import android.content.Intent;
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
    ImageView contactImage, shareImage; //declaracion de variables
    String shareContactInformation;

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

            shareContactInformation = contact.toString(); //obteniendo informacion de contacto para compartir
        }
        shareImage = view.findViewById(R.id.img_share);
        shareImage.setOnClickListener(share);//indicando a donde estara la imagen que tendra la funcionalidad del share

        return view;
    }

    View.OnClickListener share = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shareContact(); //dandole funcionalidad a imagen de share
        }
    };

    public  void shareContact(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContactInformation);
        shareIntent.setType("text/plain");

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //permiso de lectura

        Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share_contact));

        if (shareIntent.resolveActivity(getContext().getPackageManager()) != null){
            startActivity(chooser); //sendIntent
        }
    }
}