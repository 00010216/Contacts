package com.example.kcruz.contacts.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kcruz.contacts.MainActivity;
import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.beans.Communicator;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.fragment.ContactDataListFragment;
import com.example.kcruz.contacts.fragment.ContactListFragment;
import com.example.kcruz.contacts.utils.PermissionAdministrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactInformationActivity extends AppCompatActivity  /*implements ContactDataListFragment.ContactDataListListener*/{
    TextView firstName, lastName, number, contactName;
    ImageView contactImage; //declaracion de variables
    String shareContactInformation;
    Contact contact;
    Context context;
    private String mNumberToCall;
    LinearLayout informationContainer;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //context = getApplicationContext();
        setContentView(R.layout.activity_contact_information);
        //layoutInflater = LayoutInflater.from(context);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewsById();
        //define la vista respectiva al componente

        //contactImage = findViewById(R.id.contact_image);
        //contactName = findViewById(R.id.contact_name);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            contact = bundle.getParcelable("KEY"); //identifica el bundle que contiene el objeto
            System.out.println("Contacto"+ contact.toString());
            //prepareContactData(contact);
            //coloca la info guardada en el objeto y la coloca en el view respectivo
           contactName.setText(contact.getContactName());
            firstName.setText(contact.getNumber1());
            lastName.setText(contact.getEmail());
            number.setText(contact.getBirthday());
            //contactImage.setImageResource(contact.getImage());*/


            shareContactInformation = contact.toString(); //obteniendo informacion de contacto para compartir
            //loadContactData(contact);
        }



    }

    public void findViewsById(){
        //informationContainer = findViewById(R.id.information_container); //linearlayout que contendra los item
        contactImage = findViewById(R.id.contact_image);
        contactName = findViewById(R.id.contact_name);
        firstName = findViewById(R.id.number);
        lastName = findViewById(R.id.email);
        number = findViewById(R.id.birthday);
    }

  public void loadContactData(Contact contact){

        if(contact.getFirstName() != null){
            View firstNameV = layoutInflater.inflate(R.layout.contact_data_item, null); //inflando vista del item

            //asignando valores que contendra el item
            TextView dataDescription = firstNameV.findViewById(R.id.information_description);
            dataDescription.setText("First Name");//habra que hacerlo para los diferentes casos

            TextView data = firstNameV.findViewById(R.id.contact_data);
            data.setText(contact.getFirstName());
            System.out.println("First name:"+ contact.getFirstName());

            informationContainer.addView(firstNameV);
        }

        if(contact.getLastName() != null){
            View lastNameV = layoutInflater.inflate(R.layout.contact_data_item, null); //inflando vista del item

            //asignando valores que contendra el item
            TextView dataDescription = lastNameV.findViewById(R.id.information_description);
            dataDescription.setText("Last Name");//habra que hacerlo para los diferentes casos

            TextView data = lastNameV.findViewById(R.id.contact_data);
            data.setText(contact.getLastName());
            System.out.println("Last name:"+ contact.getLastName());
            informationContainer.addView(lastNameV);
        }

        if(contact.getNumber1() != null){
            View phoneV = layoutInflater.inflate(R.layout.contact_data_item, null); //inflando vista del item

            //asignando valores que contendra el item
            TextView dataDescription = phoneV.findViewById(R.id.information_description);
            dataDescription.setText("Phone");//habra que hacerlo para los diferentes casos

            TextView data = phoneV.findViewById(R.id.contact_data);
            data.setText(contact.getNumber1());
            System.out.println("Numero:"+ contact.getNumber1());

            informationContainer.addView(phoneV);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_share:
                shareContact();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void shareContact(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareContactInformation);
        shareIntent.setType("text/plain");

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //permiso de lectura

        Intent chooser = Intent.createChooser(shareIntent, getString(R.string.share_contact));

        if (shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser); //sendIntent
        }
    }


}
