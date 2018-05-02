package com.example.kcruz.contacts;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ContactFragmentList extends ListFragment implements AdapterView.OnItemClickListener {

    Contact contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //agregando elementos visuales a la vista
        View view = inflater.inflate(R.layout.fragment_list_contact, container, false);
        return view;
    }

    //Debe recibir valores del celular y de los ingresados por usuario
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.firstName, android.R.layout.simple_list_item_1);
        /*{
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };*/
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        //se envia a objeto el contenido en la posicion i segun click
        contact = new Contact(getResources().getStringArray(R.array.firstName)[i],
                getResources().getStringArray(R.array.lastName)[i],getResources().getStringArray(R.array.number)[i],
                getResources().obtainTypedArray(R.array.image).getResourceId(i,-1));

        Bundle bundle = new Bundle(); //procesa la info que se enviara a traves del intent
        bundle.putParcelable("KEY", contact); //manda identificador de bundle

        if(getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            Fragment frag = new ContactViewerFragment();
            frag.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.viewer,frag);
            fragmentTransaction.commit();
        }else {
            Toast.makeText(getActivity(), "Item: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(getActivity().getApplicationContext(), ContactInformationActivity.class);
            newIntent.setAction(Intent.ACTION_SEND);
            newIntent.putExtras(bundle);
            startActivity(newIntent);
        }

        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //Identificando si el dispositivo es de size large envia la informacion al fragmento para modo de tablet
            if(getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
                Fragment frag = new ContactViewerFragment();
                frag.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.viewer,frag);
                fragmentTransaction.commit();
            }else{
                //Envia informacion del contacto al activity respectivo para vista de pantallas mas pequeñas
                Intent newIntent = new Intent(getActivity().getApplicationContext(), ContactInformationActivity.class);
                newIntent.setAction(Intent.ACTION_SEND);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){ Toast.makeText(getActivity(), "Item: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            Intent newIntent = new Intent(getActivity().getApplicationContext(), ContactInformationActivity.class);
            newIntent.setAction(Intent.ACTION_SEND);
            newIntent.putExtras(bundle);
            startActivity(newIntent);
        }*/
    }
}
