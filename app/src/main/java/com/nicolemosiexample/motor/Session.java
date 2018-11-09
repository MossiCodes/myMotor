package com.nicolemosiexample.motor;

/**
 * Created by ADMIN on 6/27/2018.
 */

public class Session {

    String location, date, extype, reps, email, uid;

    public Session() {
        // Empty constructor
    }

    public Session( String location, String date, String extype, String reps, String email, String uid ) {
        this.location = location;
        this.date = date;
        this.extype = extype;
        this.reps = reps;
        this.email = email;
        this.uid = uid;
    }

    public String getLocation() { return this.location; }

    public String getDate() { return this.date; }

    public String getExtype() { return this.extype; }

    public String getReps() { return this.reps; }

    public String getEmail() { return this.email; }

    public String getUid() { return this.uid; }

    public void setLocation( String location ) {
        this.location = location;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public void setExtype( String extype ) {
        this.extype = extype;
    }

    public void setReps( String reps ) {
        this.reps = reps;
    }

    public void setEmail( String email ) { this.email = email; }

    public void setUid( String uid ) { this.uid = uid; }
}
