package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sfamobile.dahlia.sfamobile.R;

public class RegisteredSucessfullyActivity extends AppCompatActivity {
    Button mLogout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_sucessfully);

        mLogout = (Button) findViewById(R.id.buttonL);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_login);
            }
        });


    }
}
