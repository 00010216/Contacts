package com.example.kcruz.contacts.beans;

import java.util.List;

public interface Communicator {

    List<Contact> importContacts();
    void makeCall(String number);
    void addContact(Contact mContact);
}
