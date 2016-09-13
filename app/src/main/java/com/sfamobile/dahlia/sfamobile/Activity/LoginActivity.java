package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton = null;
    Button mPowerdByButton = null;
    Button mRegisterButton = null;
    Button mForgotPasswordButton = null;
    EditText  mUserName = null;
    EditText mPassword =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mLoginButton =(Button)findViewById(R.id.email_sign_in_button);
        mRegisterButton =(Button)findViewById(R.id.email_sign_up_button);
        mForgotPasswordButton =(Button)findViewById(R.id.forgotpassword_button);
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(i);
            }
        });

        mUserName =(EditText)findViewById(R.id.email_atv);
        mPassword =(EditText)findViewById(R.id.password_et);

        mPowerdByButton = (Button) findViewById(R.id.help_button) ;
        mPowerdByButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.dialog_powerd_by);

                Button dahliaUrlButton = (Button) dialog.findViewById(R.id.dahlia_uri_btn);
                dahliaUrlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dahlia-tech.com"));
                        startActivity(browserIntent);

                    }
                });

                Button sfaMobiUrlButton = (Button) dialog.findViewById(R.id.sfamobi_uri_btn);
                sfaMobiUrlButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sfamobi.com"));
                        startActivity(browserIntent);
                    }
                });

                ImageView cancleButton = (ImageView) dialog.findViewById(R.id.cancle_img);
                cancleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });


        mLoginButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(mUserName.getText().toString().equals("admin") &&
                        mPassword.getText().toString().equals("sfa123")) {

                    Toast.makeText(getApplicationContext(), "logged in sucessfully",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,DashBoardActivity.class);
                    startActivity(i);
                    finish();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }



        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });




    }



}