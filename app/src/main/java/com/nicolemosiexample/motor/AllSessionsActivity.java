package com.nicolemosiexample.motor;

import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllSessionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Bundle extras;
    ArrayList<Session> sessions;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_sessions);

        // Get intent
        extras = getIntent().getExtras();
        sessions = new ArrayList<>();

        getUserData(extras.getString("email"));
    }

    public void getUserData(String email) {
        mDatabase = FirebaseDatabase.getInstance().getReference("sessions");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Session session = childSnapshot.getValue(Session.class);
                        sessions.add(session);
                    }

                    showUpcomingSessions();
                } else {
                    Toast.makeText(AllSessionsActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AllSessionsActivity.this, "Ref cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showUpcomingSessions() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomSessions customSessions = new CustomSessions(this, sessions, extras);
        recyclerView.setAdapter(customSessions);
    }
}
