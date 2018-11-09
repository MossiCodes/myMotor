package com.nicolemosiexample.motor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static android.content.pm.PackageInstaller.*;

public class SessionsActivity extends AppCompatActivity  {

    Bundle extras;
    String email;
    User user;
    DatabaseReference mDatabase;
    EditText locationET, dateET, repsET;
    ImageView datePicker;
    ActionProcessButton saveWorkout;
    Spinner exerciseSpinner;
    String exercises[] = {"Aerobic exercise", "Breathing exercise", "Strength exercise", "Stretching exercise"};
    String selectedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workout sessions");

        // Get extras
        extras = getIntent().getExtras();
        email = extras.getString("email");
        getUserData( email );

        // Get views
        locationET = findViewById(R.id.session_location);
        locationET.setText( extras.getString("gym") );
        dateET = findViewById(R.id.sessions_date);
        repsET = findViewById(R.id.session_reps);

        exerciseSpinner = findViewById(R.id.sessions_repairs);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter( adapter );
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedExercise = exercises[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedExercise = exercises[0];
            }
        });

        saveWorkout = findViewById(R.id.button_session_save);
        saveWorkout.setMode( ActionProcessButton.Mode.ENDLESS );
        saveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWorkout.setProgress(1);
                saveWorkout();
            }
        });
    }

    public void getUserData( String email ) {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        user = childSnapshot.getValue(User.class);
                    }
                }else {
                    Toast.makeText( SessionsActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( SessionsActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void saveWorkout() {
        String location = locationET.getText().toString();
        String date = dateET.getText().toString();
        String reps = repsET.getText().toString();

        if( !location.isEmpty() && !date.isEmpty() && !reps.isEmpty() ) {
            mDatabase = FirebaseDatabase.getInstance().getReference("sessions");
            String sessionID = mDatabase.push().getKey();
            String selectedService = null;
            Session newSession = new Session(location, date, selectedService, reps, user.getEmail(), sessionID);

            mDatabase.child(sessionID).setValue(newSession, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if( databaseError == null ) {
                        saveWorkout.setProgress(100);
                        Toast.makeText( SessionsActivity.this, "Done", Toast.LENGTH_SHORT ).show();

                        Intent intent = new Intent(SessionsActivity.this, LogInActivity.class );
                        intent.putExtras( extras );
                        startActivity( intent );
                    }else {
                        Toast.makeText( SessionsActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT ).show();
                        saveWorkout.setProgress(0);
                    }
                }
            });
        }else {
            Toast.makeText( SessionsActivity.this, "Fill all fields", Toast.LENGTH_SHORT ).show();
        }
    }

    public void showDateFragment() {
        DialogFragment newFragment = new DatePickerTwo();
        newFragment.show( getSupportFragmentManager(), "DatePicker" );
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        // Assign the concatenated strings to dateMessage.
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        dateET.setText( dateMessage );
    }

}