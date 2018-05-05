package com.example.kcruz.contacts.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kcruz.contacts.R;
import com.example.kcruz.contacts.adapter.ContactListAdapter;
import com.example.kcruz.contacts.beans.Contact;
import com.example.kcruz.contacts.utils.SharedPreference;

import java.util.List;

public class FavoriteListFragment extends Fragment implements ContactListAdapter.ContactListClickListener {
    public static final String ARG_ITEM_ID = "favorite_list";

    RecyclerView favoriteList;
    SharedPreference sharedPreference;
    List<Contact> favorites;
    LinearLayoutManager lManager;

    Activity activity;
    ContactListAdapter contactListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list,container, false);
        //Obtener favorites items de SharedPreferences
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

        if(favorites == null){
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        }else{
            if(favorites.size() == 0){
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }
            favoriteList = (RecyclerView) view.findViewById(R.id.list_contact);
            if(favoriteList != null){
                favoriteList.setHasFixedSize(true);
                lManager = new LinearLayoutManager(container.getContext());
                favoriteList.setLayoutManager(lManager);

                contactListAdapter = new ContactListAdapter(activity, favorites, this);
                favoriteList.setAdapter(contactListAdapter);
            }
        }
        return view;
    }

    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }


    @Override
    public void onResume() {
        getActivity().setTitle(R.string.favorites);
        super.onResume();
    }

    @Override
    public void onContactClick(View v, int position) {

    }

    @Override
    public void onContactLongClick(View v, int position) {
        ImageView button = (ImageView) v
                .findViewById(R.id.imgbtn_favorite);

        String tag = button.getTag().toString();
        if (tag.equalsIgnoreCase("grey")) {
            sharedPreference.addFavorite(activity,
                    favorites.get(position));
            Toast.makeText(
                    activity,
                    activity.getResources().getString(
                            R.string.add_favr),
                    Toast.LENGTH_SHORT).show();

            button.setTag("red");
            button.setImageResource(R.drawable.ic_fav);
        } else {
            sharedPreference.removeFavorite(activity,
                    favorites.get(position));
            button.setTag("grey");
            button.setImageResource(R.drawable.ic_fav_border);
            contactListAdapter.remove(favorites
                    .get(position));
            Toast.makeText(
                    activity,
                    activity.getResources().getString(
                            R.string.remove_favr),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
