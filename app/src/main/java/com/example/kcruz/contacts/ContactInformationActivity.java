package com.example.kcruz.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInformationActivity extends AppCompatActivity {

    TextView firstName, lastName, number;
    ImageView contactImage, shareImage; //declaracion de variables
    String shareContactInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);

        //define la vista respectiva al componente
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        number = findViewById(R.id.number);
        contactImage = findViewById(R.id.contact_image);
        Bundle bundle = getIntent().getExtras();
        Contact contact;

        if(bundle != null){
            contact = bundle.getParcelable("KEY"); //identifica el bundle que contiene el objeto

            //coloca la info guardada en el objeto y la coloca en el view respectivo
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            number.setText(contact.getNumber());
            contactImage.setImageResource(contact.getImage());
            shareContactInformation = contact.toString();
         }

        //setLinks();
        shareImage = findViewById(R.id.img_share);
        shareImage.setOnClickListener(share);
    }

    View.OnClickListener share = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shareContact();
        }
    };

    /*private void setLinks(){
        TextView email = (TextView) findViewById(R.id.txt_email);
    }*/

    public  void shareContact(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContactInformation);
        shareIntent.setType("text/plain");

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //permiso de lectura

        Intent chooser = Intent.createChooser(shareIntent, "Share");

        if (shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser); //sendIntent
        }
    }

}
