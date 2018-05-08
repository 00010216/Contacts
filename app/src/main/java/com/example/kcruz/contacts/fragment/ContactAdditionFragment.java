package com.example.kcruz.contacts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.activity.ContactInformationActivity;
import com.example.kcruz.contacts.beans.Communicator;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.utils.ContactDataAdministrator;

import java.util.Calendar;

public class ContactAdditionFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = ContactAdditionFragment.class.getSimpleName();
    public static final String ARG_ITEM_ID = "add_contact";
    EditText firstName, lastName,contactNumber,email, birthday;
    Activity activity;
    Contact newContact;
    TextView doneText, cancelText;
    Button btnAdd;
    Spinner phoneSpinner;
    Spinner emailSpinner;
    int doneClicked = 0;
    Communicator mCallback;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btn_clear;

    public ContactAdditionFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Communicator) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = getActivity();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_contact_addition, container, false);
        findViewsById(view);
        getActivity().setTitle(R.string.new_contact);
        ArrayAdapter<CharSequence> phoneAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.phone_options_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(phoneAdapter);
        phoneSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> emailAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.email_options_array, android.R.layout.simple_spinner_item);
        emailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        emailSpinner.setAdapter(emailAdapter);
        emailSpinner.setOnItemSelectedListener(this);

        birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePicker = new DatePickerDialog(activity,
                            android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, year, month, day);
                    //datePicker.getDatePicker().setSpinnersShown(true);
                    datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());
                    datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePicker.getDatePicker().setCalendarViewShown(false);
                    datePicker.setTitle(getResources().getString(R.string.pick_date));
                    datePicker.show();
                    birthday.clearFocus();
                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                birthday.setText(day+"/"+month+"/"+year);
                //btn_clear.setVisibility(View.VISIBLE);
            }
        };

        /*btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthday.setText("");
                btn_clear.setVisibility(View.GONE);
            }
        });*/

        return view;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.new_contact);
        //getActivity().getActionBar().setTitle(R.string.favorites);
        super.onResume();
    }


    private void findViewsById(View view) {
        //Asignando vista a cada EditText
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        contactNumber = view.findViewById(R.id.number);
        email = view.findViewById(R.id.email);
        birthday = view.findViewById(R.id.birthday);
        phoneSpinner= (Spinner) view.findViewById(R.id.phone_options_spinner);
        emailSpinner = (Spinner) view.findViewById(R.id.email_options_spinner);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_addition,menu);
        MenuItem hideitem1 = menu.findItem(R.id.app_bar_search);
        MenuItem hideitem2 = menu.findItem(R.id.menu_favorites);
        MenuItem hideitem3 = menu.findItem(R.id.img_add_contact);
        MenuItem hideitem4 = menu.findItem(R.id.import_contacts);
        hideitem1.setVisible(false);hideitem2.setVisible(false);hideitem3.setVisible(false);hideitem4.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.menu_done:
                doneClicked++;
                //obtener informacion,(guardar)agregarla a arreglo de contactos en main activity
                Log.d(TAG, "onClick: " + doneClicked);
                getNewContactInformation();
                Log.d(TAG, "contacinfor: " + newContact.toString());
                mCallback.addContact(newContact);
                Bundle bundle = new Bundle(); //procesa la info que se enviara a traves del intent
                bundle.putParcelable("KEY", newContact); //manda identificador de bundle
                Intent intentNewContact = new Intent(getContext(), ContactInformationActivity.class);
                intentNewContact.setAction(Intent.ACTION_SEND);
                intentNewContact.putExtras(bundle);
                startActivity(intentNewContact); //iniciando la otra actividad

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getNewContactInformation(){
        //obtiene informacion del nuevo contacto
        Log.d(TAG, "getNewContactInformation: " + firstName.getText().toString().trim());
        Log.d(TAG, "getNewContactInformation: " + lastName.getText().toString().trim());
        Log.d(TAG, "getNewContactInformation: " + contactNumber.getText().toString().trim());
        newContact.setFirstName(firstName.getText().toString());
        newContact.setLastName(lastName.getText().toString());
        newContact.setNumber1(contactNumber.getText().toString());
        newContact.setEmail(email.getText().toString());
        newContact.setBirthday(birthday.getText().toString());
        Toast.makeText(getContext(), "Item: " + newContact.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
//
//    public void addContact(Contact contact) {
//        contacts.add(contact);
//        contactListAdapter.notifyItemInserted(contacts.size()-1);
//    }

    public void addContact(){
        if (!firstName.getText().toString().trim().isEmpty()){
            Contact c = new Contact();
            //c.setId(String.valueOf(ContactDataAdministrator.getInstance().generateId()));
            c.setFirstName(firstName.getText().toString().trim());
            c.setNumber1(contactNumber.getText().toString().trim());
            c.setEmail(email.getText().toString().trim());
            c.setBirthday(birthday.getText().toString().trim());
            ContactDataAdministrator.getInstance().addContact(c);
            Toast.makeText(getContext(), "El contacto fue agregado", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
        else {
            Toast.makeText(getContext(), "El nombre es requerido",Toast.LENGTH_SHORT).show();
        }
    }
}
