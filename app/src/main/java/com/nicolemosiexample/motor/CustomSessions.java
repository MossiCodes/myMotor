package com.nicolemosiexample.motor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 6/26/2018.
 */

public class CustomSessions extends RecyclerView.Adapter<CustomSessions.MyViewHolder> {

    Context context;
    Bundle extras;
    ArrayList<Session> sessions;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView seshMonth, seshDate, seshLocation, seshExercise, seshReps;

        public MyViewHolder(View view) {
            super(view);
            seshMonth = view.findViewById(R.id.textview_session_month);
            seshDate = view.findViewById(R.id.textview_session_date);
            seshLocation = view.findViewById(R.id.textview_session_gym);
            seshExercise = view.findViewById(R.id.textview_session_extype);
        }
    }

    public CustomSessions(Context context, ArrayList<Session> sessions, Bundle extras ) {
        this.context = context;
        this.sessions = sessions;
        this.extras = extras;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.custom_sessions, parent, false );
        return new MyViewHolder( v );
    }

    @Override
    public void onBindViewHolder(CustomSessions.MyViewHolder holder, int position) {
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String split_date[] = sessions.get(position).getDate().split("/");
        String month = months[ Integer.parseInt( split_date[0] ) - 1 ];

        holder.seshMonth.setText( month );
        holder.seshDate.setText( split_date[1] );
        holder.seshLocation.setText( sessions.get(position).getLocation() );
        holder.seshExercise.setText( "Type : " + sessions.get(position).getExtype() );
        // holder.seshReps.setText( "Reps : " + sessions.get(position).getReps() );
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }
}