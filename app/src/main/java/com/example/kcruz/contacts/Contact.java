package com.example.kcruz.contacts;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String firstName;
    private String lastName;
    private String number;
    private int image;
    //private String birthDate;
    //private String email;

    public Contact(){
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }

    public Contact(String firstName, String lastName, String number, int image){
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.image = image;

    }

    protected Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        number = in.readString();
        image = in.readInt();
        //birthDate = in.readString();
        // email = in.readString();
    }

    public Contact(String firstName, String lastName, String number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(number);
        dest.writeInt(image);
        /*dest.writeString(birthDate);
        dest.writeString(email);*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

   /* public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    @Override
    public String toString() { //HACER IF PARA VER LENGUAJE, DEPENDE SI TIENE O NO ESOS CONTENIDO
        return "Name:" + this.getFirstName() + "\n Last Name:" + this.getLastName() + "\n Number:" + this.getNumber()+"";
    }

    //metodo para representar nombre de contacto en la vista de informacion de contacto activity_contact_information
    public  String contactName (){
        return this.getFirstName() + " " + this.getLastName();
    }
}
