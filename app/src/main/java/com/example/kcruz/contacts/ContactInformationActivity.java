package com.example.kcruz.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInformationActivity extends AppCompatActivity {

    TextView firstName, lastName, number;
    ImageView contactImage; //declaracion de variables


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
        }
    }
}
