package com.example.kcruz.contacts.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private String number1;
    private String email;
    private String birthday;

    public Contact() {
        super();
        firstName = "";
        lastName = "";
        number1 = "";
        email = "";
        birthday = "";
    }

    public Contact(int id, String firstName, String lastName, String number1, String email, String birthday) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number1 = number1;
        this.email = email;
        this.birthday = birthday;
    }

    protected Contact(Parcel in) {

    }

    public Contact(String s, String s1, String s2) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(number1);
        dest.writeString(email);
        dest.writeString(birthday);
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

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (id != other.id)
            return false;
        return true;
    }

    /*@Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + firstName + ", description="
                + lastName + ", number=" + number1 + "]";
    }*/

    @Override
    public String toString() {
        return "id "+ id + "-firstName " + firstName + "-lastName " + lastName + "-number1 " + number1
        + "-email " + email +"-birthday " + birthday;
    }

    //metodo para representar nombre de contacto en la vista de informacion de contacto activity_contact_information
    public  String getContactName (){
        return this.getFirstName() + " " + this.getLastName();
    }
}
