package com.example.kcruz.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactAdditionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText firstName, lastName,contactNumber;
    Contact newContact;
    TextView doneText, cancelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_addition);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); *///hacer que el ToolBar actue como el ActionBar para esta actividad

        //Asignando vista a cada EditText
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        contactNumber = findViewById(R.id.number);

        doneText = findViewById(R.id.txt_done);
        doneText.setOnClickListener(this);
        cancelText = findViewById(R.id.txt_cancel);
        cancelText.setOnClickListener(this);//indicando a donde estara la imagen que tendra la funcionalidad del share
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_done:
                //obtener informacion,(guardar)agregarla a arreglo de contactos, abrir pantalla con info de nuevo contacto
                goToActivity(v);
                break;
            case R.id.txt_cancel:
                //no guardar nada y regresar a la pantalla de inicio
                goToActivity(v);
                break;
            default:
                break;
        }
    }

    public void goToActivity(View view){
        switch (view.getId()){
            case R.id.txt_done:
                sendNewContactInformation(getNewContactInformation());
                break;
            case R.id.txt_cancel:
                //regresar a actividad principal MainActivity
                Intent intentBackToMain = new Intent(this, MainActivity.class);
                startActivity(intentBackToMain); //iniciando la otra actividad
                break;
            default:
                break;
        }
    }

    public Contact getNewContactInformation(){
        newContact = new Contact(firstName.getText().toString(),lastName.getText().toString(),contactNumber.getText().toString());
        Toast.makeText(this, "Item: " + newContact.toString(), Toast.LENGTH_SHORT).show();
        return newContact;
    }

    public void sendNewContactInformation(Contact newContact){
        //ir a actividad que muestra informacion del nuevo contacto y enviar informacion a actividad
        Bundle bundle = new Bundle(); //procesa la info que se enviara a traves del intent
        bundle.putParcelable("KEY", newContact); //manda identificador de bundle
        Intent intentNewContact = new Intent(this, ContactInformationActivity.class);
        intentNewContact.setAction(Intent.ACTION_SEND);
        intentNewContact.putExtras(bundle);
        startActivity(intentNewContact); //iniciando la otra actividad

    }
}
