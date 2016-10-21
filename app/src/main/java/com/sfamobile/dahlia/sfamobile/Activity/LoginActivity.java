package com.sfamobile.dahlia.sfamobile.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity" ;
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


        isStoragePermissionGranted();



    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this,"Permission is granted",Toast.LENGTH_SHORT).show();
                return true;
            } else {


                Toast.makeText(LoginActivity.this,"Permission is revoked",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            Toast.makeText(LoginActivity.this,"Permission is granted Else",Toast.LENGTH_SHORT).show();
            return true;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            Toast.makeText(LoginActivity.this,"Permission: "+permissions[0]+ "was "+grantResults[0],Toast.LENGTH_SHORT).show();

        }
    }
}