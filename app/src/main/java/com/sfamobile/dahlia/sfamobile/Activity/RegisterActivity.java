package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button mButtonRegister = null;
    Button mBackButton = null;
    EditText mEnterUserName = null;
    EditText mEnterPassword = null;
    EditText mConformPassword = null;
    EditText mEnterEmail = null;
    EditText mPhoneNumber = null;
    EditText mCompany = null;
    EditText mCountryCode =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialization of all edit texts
        mEnterUserName = (EditText) findViewById(R.id.edit_Username);
        mEnterPassword = (EditText) findViewById(R.id.edit_password);
        mConformPassword =(EditText)findViewById(R.id.edit_ConformPassword);
        mEnterEmail = (EditText) findViewById(R.id.edit_Email);
        mPhoneNumber = (EditText) findViewById(R.id.Mobile_number);
        mCompany = (EditText) findViewById(R.id.Company_Name);
        mCountryCode = (EditText) findViewById(R.id.code);

        //Initialization of Register Button
        mButtonRegister = (Button) findViewById(R.id.register_button2);
        mBackButton = (Button) findViewById(R.id.back_button);



        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_login);
            }
        });


        mButtonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                boolean isValid = false;


                String sFirstName =  mEnterUserName.getText().toString();
                String sPassword = mEnterPassword.getText().toString();
                String sRe_psw =  mConformPassword.getText().toString();
                String sEmailId = mEnterEmail.getText().toString();
                String sPhoneNo = mPhoneNumber.getText().toString();
                String sCountrycode = mCountryCode.getText().toString();
                String sCompany = mCompany.getText().toString();


                if(!validateFields(sFirstName))
                {
                    mEnterUserName.setError("username name should not be null");
                }else if(!validateFields(sPassword))
                {
                    mEnterPassword.setError("password should not be blank");
                }else if(!(sPassword.length() > 5))

                {
                    mEnterPassword.setError("password length must be minimum 6 characters");
                }

                else if(!validateFields(sRe_psw))
                {
                    mConformPassword.setError("this field should not be blank");
                }else if(!mEnterPassword.getText().toString().equals(mConformPassword.getText().toString()))

                {
                    mConformPassword.setError("password not matched");
                }
                else if(!validateFields(sEmailId))
                {
                    mEnterEmail.setError("email should not be blank");
                }else if(!validateEmail(sEmailId))
                {

                    mEnterEmail.setError(" invalid mail Id");

                } else if(!validateFields(sPhoneNo))
                {
                    mPhoneNumber.setError("mobile number  should not be blank");
                }
                else if(!validateFields(sCompany))
                {
                    mCompany.setError("company name  should not be blank");
                }


                else if(!validateFields(sCountrycode))
                {
                    mCountryCode.setError("country code should not be blank");
                }
                else if(!(sCountrycode.length() == 2))
                {
                    mCountryCode.setError("country code should be 2 digits");
                }

                else
                {
                    mEnterUserName.setError(null);
                    mEnterPassword.setError(null);
                    mConformPassword.setError(null);
                    mEnterEmail.setError(null);
                    mPhoneNumber.setError(null);
                    mCompany.setError(null);
                    mCountryCode.setError(null);

                    Toast.makeText(RegisterActivity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),RegisteredSucessfullyActivity.class);
                    startActivity(i);
                    setContentView(R.layout.activity_registered_sucessfully);
                }



            }


        });


    }

//*******.................method for valiadting all edit text feilds................*************//

    public boolean validateFields(String fields)

    {
        return fields.length() > 0;
    }

    //*******.................method for valiadting email................*************//
    public boolean validateEmail(String edtEmail) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(edtEmail);
        return matcher.matches();
    }

}