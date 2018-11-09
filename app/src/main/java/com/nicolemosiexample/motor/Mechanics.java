package com.nicolemosiexample.motor;

/**
 * Created by ADMIN on 7/5/2018.
 */

public class Mechanics {

    private String name, phonenumber, email, gender, uid;
    private int imageUrl;

    public Mechanics() {
        // empty constructor
    }

    public Mechanics( int imageUrl, String name, String phonenumber, String email, String gender, String uid ) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.phonenumber = phonenumber;
        this.email = email;
        this.gender = gender;
        this.uid = uid;
    }

    public int getImageUrl() { return this.imageUrl; }

    public String getName() { return this.name; }

    public String getPhonenumber() { return this.phonenumber; }

    public String getEmail() { return this.email; }

    public String getGender() { return this.gender; }

    public String getUid() { return this.uid; }

    public void setImageUrl( int imageUrl ) { this.imageUrl = imageUrl; }

    public void setName( String name ) {
        this.name = name;
    }

    public void setPhonenumber( String phonenumber ) {
        this.phonenumber = phonenumber;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public void setGender( String gender ) {
        this.gender = gender;
    }

    public void setUid( String uid ) { this.uid = uid; }
}
