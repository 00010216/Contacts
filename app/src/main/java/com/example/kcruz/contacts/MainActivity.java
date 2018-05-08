package com.example.kcruz.contacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kcruz.contacts.activity.ContactInformationActivity;
import com.example.kcruz.contacts.beans.Communicator;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.fragment.ContactAdditionFragment;
import com.example.kcruz.contacts.fragment.ContactListFragment;
import com.example.kcruz.contacts.fragment.FavoriteListFragment;
import com.example.kcruz.contacts.utils.ContactDataAdministrator;
import com.example.kcruz.contacts.utils.PermissionAdministrator;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Communicator {

    public static String CONTACT_LIST_FRAGMENT = "CONTACT_LIST_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Contact> mContacts;
    private boolean hasAskedForCallPermission = false;
    private Fragment contentF;
    ContactListFragment contactListFragment;
    FavoriteListFragment favoriteListFragment;
    ContactAdditionFragment contactAdditionFragment;
    public static AppCompatActivity appActivity;
    LinearLayout linearLayout;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Super ejecutado");
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) mContacts = savedInstanceState.getParcelableArrayList("contacts");

        linearLayout = findViewById(R.id.viewer);
        appActivity = this;

        if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            setToolBar(R.id.left_toolbar);
            setToolBar(R.id.right_toolbar);
        } else {
            setToolBar(R.id.toolbar);
        }

        System.out.println("activity_main colocado");
        FragmentManager fm = getSupportFragmentManager();
        System.out.println("FragmentManager iniciado");
        if (savedInstanceState != null) {
            System.out.println("Se encontró Bundle");
            if (savedInstanceState.containsKey("myContent")) {
                String content = savedInstanceState.getString("myContent");
                if (content.equals(FavoriteListFragment.ARG_ITEM_ID)) {
                    if (fm.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.favorites);
                        contentF = fm.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID);
                    }
                }
            }

            if (fm.findFragmentByTag(ContactListFragment.ARG_ITEM_ID) != null) {
                contactListFragment = (ContactListFragment) fm.findFragmentByTag(ContactListFragment.ARG_ITEM_ID);
                contentF = contactListFragment;
            }
        } else {
            System.out.println("No se encontró Bundle");
            contactListFragment = new ContactListFragment();
            System.out.println("productListFragment inicializado");
            setFragmentTitle(R.string.app_name);
            System.out.println("Titulo de fragment colocado");
            switchContent(contactListFragment, ContactListFragment.ARG_ITEM_ID);
            System.out.println("Contenido colocado");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentF instanceof FavoriteListFragment)
            outState.putString("myContent", FavoriteListFragment.ARG_ITEM_ID);
        else outState.putString("myContent", ContactListFragment.ARG_ITEM_ID);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            getMenuInflater().inflate(R.menu.main, menu);
            getMenuInflater().inflate(R.menu.main_large_size, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorites:
                setFragmentTitle(R.string.favorites);
                favoriteListFragment = new FavoriteListFragment();
                switchContent(favoriteListFragment, FavoriteListFragment.ARG_ITEM_ID);
                break;
            case R.id.import_contacts:

                if(PermissionAdministrator.hasPermission(this, Manifest.permission.READ_CONTACTS)){
                    mContacts = ContactDataAdministrator.getInstance().getContactList();
                } else {
                    PermissionAdministrator.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, PermissionAdministrator.READ_CONTACTS_CODE);
                }
                break;
            case R.id.img_add_contact:
                if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
                    setFragmentTitle(R.string.add_contact);
                    contactAdditionFragment = new ContactAdditionFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    while (fm.popBackStackImmediate()) ;

                    if (contactAdditionFragment != null) {
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.viewer,contactAdditionFragment, ContactAdditionFragment.ARG_ITEM_ID);

                        //Solo FavoriteListFragment se agrega al backStack

                        transaction.commit();
                        contentF = contactAdditionFragment;
                    }
                } else {
                    setFragmentTitle(R.string.add_contact);
                    contactAdditionFragment = new ContactAdditionFragment();
                    switchContent(contactAdditionFragment, ContactAdditionFragment.ARG_ITEM_ID);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.popBackStackImmediate()) ;

        if (fragment != null) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFrame, fragment, tag);

            //Solo FavoriteListFragment se agrega al backStack

            if (!(fragment instanceof ContactListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentF = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getSupportActionBar().setTitle(resourseId);
    }

    /*
     * Se cierra la aplicacion cuando el stack tenga 0, es decir
     * cuando se tenga desplegado un ProductListFragment
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            setFragmentTitle(R.string.app_name); //asigna de vuelta el nombre al ToolBar
            super.onBackPressed();
        } else if (contentF instanceof ContactListFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    public void setToolBar(int idToolBar) {
        Toolbar toolbar = (Toolbar) findViewById(idToolBar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    public List<Contact> importContacts() {
        return mContacts;
    }

    @Override
    public void makeCall(String number) {

    }

    @Override
    public void addContact(Contact mContact) {
        contactListFragment.addContact(mContact);
    }

    public static Context getContext(){
        return appActivity.getApplicationContext();
    }
}

