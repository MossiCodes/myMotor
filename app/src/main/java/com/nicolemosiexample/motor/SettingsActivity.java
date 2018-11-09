package com.nicolemosiexample.motor;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    String language = "english";
    ActionProcessButton saveChanges;
    Locale myLocale;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_settings);

        extras = getIntent().getExtras();

        // Init
        saveChanges = findViewById(R.id.button_language_save);
        saveChanges.setMode(ActionProcessButton.Mode.ENDLESS);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( language == "english" ) {
                    setLocale("en");
                }else if( language == "kiswahili" ) {
                    setLocale("sw");
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_english:
                if (checked)
                    language = "english";
                break;
            case R.id.radio_kiswahili:
                if (checked)
                    // Ninjas rule
                    language = "kiswahili";
                break;
        }
    }

    public void setLocale(String language) {
        myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration( conf, dm );

        Intent refresh = new Intent( SettingsActivity.this, LogInActivity.class );
        refresh.putExtras( extras );
        startActivity( refresh );
    }

}