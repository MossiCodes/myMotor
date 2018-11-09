package com.nicolemosiexample.motor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MechanicActivity extends AppCompatActivity {

    Bundle extras;
    RecyclerView recyclerView;
    ArrayList<String> iNames, iGender, iEmail, iPhone;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_instructor);

        // Get bundle
        extras = getIntent().getExtras();

        // Init instructors
        iNames = new ArrayList<>();
        iGender = new ArrayList<>();
        iEmail = new ArrayList<>();
        iPhone = new ArrayList<>();

        getInstructors();
    }

    public void getInstructors() {
        mDatabase = FirebaseDatabase.getInstance().getReference("instructors");
        mDatabase.orderByChild("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Mechanics mechanic = childSnapshot.getValue(Mechanics.class);
                        iNames.add(mechanic.getName());
                        iGender.add(mechanic.getGender());
                        iEmail.add(mechanic.getEmail());
                        iPhone.add(mechanic.getPhonenumber());
                    }

                    showInstructors();
                } else {
                    Toast.makeText(MechanicActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MechanicActivity.this, "Ref cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showInstructors() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomMechanics customMechanics = new CustomMechanics(iNames, iGender, iEmail, iPhone);
        recyclerView.setAdapter(customMechanics);
    }
}

