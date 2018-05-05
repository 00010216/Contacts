package com.example.kcruz.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kcruz.contacts.fragment.ContactListFragment;
import com.example.kcruz.contacts.fragment.FavoriteListFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment contentF;
    ContactListFragment contactListFragment;
    FavoriteListFragment favoriteListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Super ejecutado");
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        //toolbar.setSubtitle("Subtitle");

        setSupportActionBar(toolbar);
        System.out.println("activity_main colocado");
        FragmentManager fm = getSupportFragmentManager();
        System.out.println("FragmentManager iniciado");
        if(savedInstanceState != null){
            System.out.println("Se encontró Bundle");
            if(savedInstanceState.containsKey("myContent")){
                String content = savedInstanceState.getString("myContent");
                if(content.equals(FavoriteListFragment.ARG_ITEM_ID)){
                    if(fm.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID) != null){
                        setFragmentTitle(R.string.favorites);
                        contentF = fm.findFragmentByTag(FavoriteListFragment.ARG_ITEM_ID);
                    }
                }
            }

            if(fm.findFragmentByTag(ContactListFragment.ARG_ITEM_ID) != null){
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
        if( contentF instanceof FavoriteListFragment) outState.putString("myContent", FavoriteListFragment.ARG_ITEM_ID);
        else outState.putString("myContent", ContactListFragment.ARG_ITEM_ID);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_favorites:
                setFragmentTitle(R.string.favorites);
                favoriteListFragment = new FavoriteListFragment();
                switchContent(favoriteListFragment, FavoriteListFragment.ARG_ITEM_ID);
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.popBackStackImmediate());

        if (fragment != null){
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFrame, fragment, tag);

            //Solo FavoriteListFragment se agrega al backStack

            if(!(fragment instanceof ContactListFragment)){
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
            super.onBackPressed();
        } else if (contentF instanceof ContactListFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}
