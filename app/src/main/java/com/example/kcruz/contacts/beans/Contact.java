package com.example.kcruz.contacts.beans;

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String number;

    public Contact() {
        super();
    }

    public Contact(int id, String firstName, String lastName, String number) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if(id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Contact = [id="+id + ", name=" + firstName + ", last name = " + lastName + ", number=" +number;
    }

    public String getContactName(){
        return firstName + " " + lastName;
    }
}
