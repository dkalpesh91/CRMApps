package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewCompanyContactActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mCompanyAddressET = null;
    EditText mCompanyEmailET = null;
    EditText mCompanyPhoneET = null;

    EditText mContactEmailET = null;
    EditText mContactPhoneNumberET = null;
    EditText mContactRollET = null;

    TextInputLayout mCompanyAddressTIL = null;
    TextInputLayout mCompanyEmailTIL = null;
    TextInputLayout mCompanyPhoneTIL = null;

    AutoCompleteTextView mCompanyNameATV = null;
    AutoCompleteTextView mContactNameATV = null;

    Button mSaveButton = null;

    String mCompanyName = null;
    String mCompanyAddress = null;
    String mCompanyEmail = null;
    String mCompanyPhone = null;
    String mContactName = null;
    String mContactEmail = null;
    String mContactAddress = null;
    String mContactNumber = null;
    String mContactRoll = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    String[] company = {"Dahlia", "Wipro", "IBM", "Microsoft"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};

    boolean isFromNewLead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_company_contact);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFromNewLead = bundle.getBoolean("isFromNewLead");
        }
        initView();
    }

    void initView() {

        mSaveButton = (Button) findViewById(R.id.save_btn);

        mCompanyNameATV = (AutoCompleteTextView) findViewById(R.id.company_name_atv);
        ArrayAdapter companyName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, company);
        mCompanyNameATV.setAdapter(companyName);
        mCompanyNameATV.setThreshold(1);


        mContactNameATV = (AutoCompleteTextView) findViewById(R.id.contact_name_atv);
        ArrayAdapter contactName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contact);
        mContactNameATV.setAdapter(contactName);
        mContactNameATV.setThreshold(1);

        mCompanyAddressET = (EditText) findViewById(R.id.company_address_et);
        mCompanyEmailET = (EditText) findViewById(R.id.company_email_et);
        mCompanyPhoneET = (EditText) findViewById(R.id.company_phoneno_et);

        mContactPhoneNumberET = (EditText) findViewById(R.id.contact_number_et);
        mContactEmailET = (EditText) findViewById(R.id.contact_email_et);
        mContactRollET = (EditText) findViewById(R.id.contact_roll_et);


        mCompanyAddressTIL = (TextInputLayout) findViewById(R.id.company_address_til);
        mCompanyEmailTIL = (TextInputLayout) findViewById(R.id.company_email_til);
        mCompanyPhoneTIL = (TextInputLayout) findViewById(R.id.company_phoneno_til);

        mSaveButton.setOnClickListener(this);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Create Contact");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mActivityNameTV.setText("Edit Contact");
            mCompanyName = bundle.getString("clientName");
            mCompanyAddress = bundle.getString("clientAddress");;
            mCompanyEmail = bundle.getString("clientEmail");;
            mCompanyPhone = bundle.getString("clientPhone");
            mContactName = bundle.getString("contactName");
            mContactEmail = bundle.getString("contactemail");;
            mContactNumber = bundle.getString("contactphone");
            mContactRoll = bundle.getString("contactRole");

            mCompanyNameATV.setText(mCompanyName);
            mCompanyAddressET.setText(mContactAddress);
            mCompanyEmailET.setText(mCompanyEmail);
            mCompanyPhoneET.setText(mCompanyPhone);

            mContactNameATV.setText(mContactName);
            mContactPhoneNumberET.setText(mContactNumber);
            mContactEmailET.setText(mContactEmail);
            mContactRollET.setText(mContactRoll);
        }

    }


    public void validateData() {
        hideKeyboard();


        mCompanyName = mCompanyNameATV.getText().toString();
        mCompanyAddress = mCompanyAddressET.getText().toString();
        mCompanyEmail = mCompanyEmailET.getText().toString();
        mCompanyPhone = mCompanyPhoneET.getText().toString();
        mContactName = mContactNameATV.getText().toString();
        mContactEmail = mContactEmailET.getText().toString();
        mContactNumber = mContactPhoneNumberET.getText().toString();
        mContactRoll = mContactRollET.getText().toString();


        if (!validateString(mCompanyName)) {
            mCompanyNameATV.setError("Company name should not be null");
        } else if (!validateString(mCompanyAddress)) {
            mCompanyAddressET.setError("Company address should not be null");
        } else if (!validateEmail(mCompanyEmail)) {
            mCompanyEmailET.setError("Enter valid email id");
        } else if (!validatePhoneNumber(mCompanyPhone)) {
            mCompanyPhoneET.setError("Phone number must be 10 digit");
        } else if (!validateString(mContactName)) {
            mContactNameATV.setError("Name should not be null");

        }else if (!validatePhoneNumber(mContactNumber)) {
            mContactPhoneNumberET.setError("Phone number must be 10 digit");

        }else if (!validateEmail(mContactEmail)) {
            mContactEmailET.setError("Enter valid email id");

        }else if (!validateString(mContactRoll)) {
            mContactRollET.setError("Contact roll should not be null");

        }else {
            mCompanyNameATV.setError(null);
            mCompanyAddressET.setError(null);
            mCompanyEmailET.setError(null);
            mCompanyPhoneET.setError(null);
            mContactNameATV.setError(null);
            mContactRollET.setError(null);
            mContactEmailET.setError(null);
            mContactPhoneNumberET.setError(null);


            Toast.makeText(AddNewCompanyContactActivity.this, "Contact created", Toast.LENGTH_SHORT).show();

            String message=mCompanyNameATV.getText().toString();
            Intent intent=new Intent();
            intent.putExtra("COMPANYNAME",message);
            setResult(2,intent);
            finish();

//            Intent intent = new Intent(AddNewCompanyContact.this, ManageCompanyContactActivity.class);
//            intent.putExtra("companyName", mCompanyNameATV.getText().toString());
//            intent.putExtra("companyEmail",mCompanyEmailET.getText().toString() );
//            intent.putExtra("companyPhone", mCompanyPhoneET.getText().toString());
//            intent.putExtra("companyAddress", mCompanyAddressET.getText().toString());
//            startActivity(intent);

        }
    }

    public boolean validateString(String data) {
        return data.length() > 0;
    }

    public boolean validatePhoneNumber(String data) {
        return data.length() == 10;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean validateEmail(String email) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.save_btn:
                validateData();
                break;

            case R.id.back_arrow_img:
                if (isFromNewLead) {
                    Intent intent2 = new Intent(AddNewCompanyContactActivity.this, NewLeadActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Intent intent2 = new Intent(AddNewCompanyContactActivity.this, ManageCompanyContactActivity.class);
                    startActivity(intent2);
                    finish();
                }
                finish();
                break;

            default:
                break;
        }

    }
}