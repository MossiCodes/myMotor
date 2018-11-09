package iamprogrammer.brian.com.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.nicolemosiexample.motor.LogInActivity;
import com.nicolemosiexample.motor.R;

import com.dd.processbutton.iml.ActionProcessButton;
import com.nicolemosiexample.motor.SignUp;

public class SplashActivity extends AppCompatActivity {

    ActionProcessButton loginbtn, signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find views
        loginbtn = findViewById(R.id.button_login_splash);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SplashActivity.this, LogInActivity.class );
                startActivity( intent );
            }
        });

        signupbtn = findViewById(R.id.button_signup_splash);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SplashActivity.this, SignUp.class );
                startActivity( intent );
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}