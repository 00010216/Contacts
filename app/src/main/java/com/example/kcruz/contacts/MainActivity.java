package com.example.kcruz.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView addContactIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContactIcon = findViewById(R.id.img_add_contact);
        addContactIcon.setOnClickListener(add);
    }

    View.OnClickListener add = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddContact(v); //dandole funcionalidad a imagen para abrir activity ContactAddition
        }
    };


    public void openAddContact(View view) {
        //como segundo parametro se indica la actividad a la que ira este intent
        Intent intent = new Intent(this, ContactAdditionActivity.class);
        startActivity(intent); //iniciando la otra actividad
    }
}
