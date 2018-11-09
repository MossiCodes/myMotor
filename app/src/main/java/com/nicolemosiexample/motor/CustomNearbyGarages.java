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

public class CustomNearbyGarages extends RecyclerView.Adapter<CustomNearbyGarages.MyViewHolder> {

    Context context;
    Bundle extras;
    ArrayList<String> npNames, npCords, npLocation, npVicinity;
    GoogleMap map;
    int global_pos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gymName, gymCoords;
        ActionProcessButton showOnMap, bookSession;

        public MyViewHolder(View view) {
            super(view);
            gymName = view.findViewById(R.id.garage_name);
            gymCoords = view.findViewById(R.id.garage_coordinates);
            showOnMap = view.findViewById(R.id.button_showonmap);
            bookSession = view.findViewById(R.id.button_booksession);
        }
    }

    public CustomNearbyGarages(Context context, ArrayList<String> npNames, ArrayList<String> npCords, ArrayList<String> npLocation,
                            ArrayList<String> npVicinity, GoogleMap map, Bundle extras) {
        this.context = context;
        this.npNames = npNames;
        this.npCords = npCords;
        this.npLocation = npLocation;
        this.npVicinity = npVicinity;
        this.map = map;
        this.extras = extras;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nearbygarages, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomNearbyGarages.MyViewHolder holder, int position) {
        global_pos = position;
        holder.gymName.setText(npNames.get(position));
        holder.gymCoords.setText(npCords.get(position));
        holder.showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coords[] = npCords.get(global_pos - 1).split(",");
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])), 15));
            }
        });
        holder.bookSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SessionsActivity.class);
                extras.putString("gym", npNames.get(global_pos - 1));
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return npNames.size();
    }
}















