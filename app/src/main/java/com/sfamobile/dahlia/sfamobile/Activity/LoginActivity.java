package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton = null;
    Button mRegisterButton = null;
    Button mForgotPasswordButton = null;
    EditText  mUserName = null;
    EditText mPassword =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mLoginButton =(Button)findViewById(R.id.login_button);
        mRegisterButton =(Button)findViewById(R.id.register_button);
        mForgotPasswordButton =(Button)findViewById(R.id.forgotpassword_button);

        mUserName =(EditText)findViewById(R.id.enter_username_hint);
        mPassword =(EditText)findViewById(R.id.enter_password_hint);


        mLoginButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(mUserName.getText().toString().equals("admin") &&
                        mPassword.getText().toString().equals("admin")) {

                    Toast.makeText(getApplicationContext(), "logged in sucessfully",Toast.LENGTH_SHORT).show();
                    mUserName.setText("");
                    mPassword.setText("");

                    Intent i = new Intent(getApplicationContext(),LoggedInActivity.class);
                    startActivity(i);
                    setContentView(R.layout.activity_logged_in);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }



        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_register);
            }
        });




    }



}