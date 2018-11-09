package com.nicolemosiexample.motor;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADMIN on 6/8/2018.
 */

public class User {

    private String uid, username, email, password, dob, sex, home, current_mileage, target_mileage;

    public User() {
        // Empty constructor
    }

    public User(String uid, String username, String email, String pass, String dob, String sex, String home, String current_mileage, String target_mileage) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = pass;
        this.dob = dob;
        this.sex = sex;
        this.home = home;
        this.current_mileage= current_mileage;
        this.target_mileage = target_mileage;
    }

    public String getUid() { return this.uid; }

    public String getUsername() { return this.username; }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getDob() { return this.dob; }

    public String getSex() { return this.sex; }

    public String getHome() { return this.home; }

    public String getInit_weight() { return this.current_mileage; }

    public String getTarget_weight() { return this.target_mileage; }

    public void setUid( String uid ) { this.uid = uid; }

    public void setUsername( String username ) { this.username = username; }

    public void setEmail( String email ) { this.email = email; }

    public void setPassword( String pass ) { this.password = pass; }

    public void setDob( String dob ) { this.dob = dob; }

    public void setSex( String sex ) { this.sex = sex; }

    public void setHome( String home ) { this.home = home; }

    public void setInit_weight( String init_weight ) { this.current_mileage = current_mileage; }

    public void setTarget_weight( String target_weight ) { this.target_mileage = target_mileage; }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", this.uid );
        result.put("username", this.username);
        result.put("email", this.email);
        result.put("password", this.password);
        result.put("dob", this.dob);
        result.put("sex", this.sex);
        result.put("home", this.home);
        result.put("current_mileage", this.current_mileage);
        result.put("target_mileage", this.target_mileage);

        return result;
    }


}