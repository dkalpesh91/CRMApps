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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewLeadActivity extends AppCompatActivity implements View.OnClickListener {

    Button mSaveButton = null;
    Button mConvertOppButton = null;

    Button mAddNewContactButton = null;
    Button mAddNewPersonContactButton = null;

    TextView mLeadStatusTV = null;

    boolean isFromNewLead = false;

    AutoCompleteTextView mCompanyNameATV = null;
    AutoCompleteTextView mContactNameATV = null;

    EditText mLeadSizeOfCompanyET = null;
    EditText mDetailDescriptipnET = null;

    TextInputLayout mLeadSizeOfCompanyTIL = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    CheckBox mHotChkBox = null;
    CheckBox mWarmChkBox = null;
    CheckBox mColdChkBox = null;

    String[] company = {"Dahlia", "Wipro", "IBM", "Microsoft"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};

    String mCompanyName = null;
    String mLeadSizeOfCompany = null;
    String mWhatYouNeed = null;
    String mContactName = null;

    String mLeadStatus = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);
        initView();
        validateCheckBox();
    }

    void initView() {



        mSaveButton = (Button) findViewById(R.id.save_btn);
        mConvertOppButton = (Button) findViewById(R.id.convert_to_oppertunity_btn);

        mAddNewContactButton = (Button) findViewById(R.id.add_new_contact_btn);
        mAddNewPersonContactButton = (Button) findViewById(R.id.add_new_person_contact_btn);

        mLeadStatusTV = (TextView) findViewById(R.id.lead_status_tv);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Create New Lead");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);

        mCompanyNameATV = (AutoCompleteTextView) findViewById(R.id.company_name_atv);
        ArrayAdapter companyName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, company);
        mCompanyNameATV.setAdapter(companyName);
        mCompanyNameATV.setThreshold(1);


        mContactNameATV = (AutoCompleteTextView) findViewById(R.id.contact_name_atv);
        ArrayAdapter contactName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contact);
        mContactNameATV.setAdapter(contactName);
        mContactNameATV.setThreshold(1);

        mLeadSizeOfCompanyET = (EditText) findViewById(R.id.lead_size_of_company_et);
        mDetailDescriptipnET = (EditText) findViewById(R.id.detail_descriptipn_et);

        mLeadSizeOfCompanyTIL = (TextInputLayout) findViewById(R.id.lead_size_of_company_til);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCompanyName = bundle.getString("clientName");
            mContactName = bundle.getString("ContactName");;
            mLeadSizeOfCompany = bundle.getString("leadSize");;
            mWhatYouNeed = bundle.getString("desc");
            mActivityNameTV.setText("Edit Lead");
            mCompanyNameATV.setText(mCompanyName);
            mContactNameATV.setText(mContactName);
            mLeadSizeOfCompanyET.setText(mLeadSizeOfCompany);
            mDetailDescriptipnET.setText(mWhatYouNeed);
        }

//        mCompanyNameATV.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                boolean isContainString = false;
//                for(int i=0;i<company.length;i++){
//                    String containString = company[i];
//                    if (containString.contains(s)) {
//                        isContainString = true;
//                        break;
//                    }
//
//                }
//                if(isContainString){
//                    isContainString = false;
//                    mCompanyAddressTIL.setVisibility(View.GONE);
//                    mCompanyEmailTIL.setVisibility(View.GONE);
//                    mCompanyPhoneTIL.setVisibility(View.GONE);
//                }else{
//                    mCompanyAddressTIL.setVisibility(View.VISIBLE);
//                    mCompanyEmailTIL.setVisibility(View.VISIBLE);
//                    mCompanyPhoneTIL.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//           }
//        });

        mSaveButton.setOnClickListener(this);
        mAddNewContactButton.setOnClickListener(this);
        mAddNewPersonContactButton.setOnClickListener(this);
        mConvertOppButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.save_btn:
                validateData();
                break;

            case R.id.add_new_contact_btn:
                Intent intent = new Intent(NewLeadActivity.this,AddNewCompanyContactActivity.class);
                isFromNewLead = true;
                intent.putExtra("isFromNewLead",isFromNewLead);
                startActivity(intent);
                finish();
                break;

            case R.id.add_new_person_contact_btn:
                Intent intent1 = new Intent(NewLeadActivity.this,AddingContactsActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.back_arrow_img:
                Intent intent2 = new Intent(NewLeadActivity.this,ManageLeadActivity.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.convert_to_oppertunity_btn:
                Intent intent3 = new Intent(NewLeadActivity.this,ConvertLeadToOpportunityActivity.class);
                isFromNewLead = true;
                intent3.putExtra("isFromNewLead",isFromNewLead);
                startActivity(intent3);
                finish();
                break;

            default:
                break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("COMPANYNAME");
            mCompanyNameATV.setText(message);
        }
    }

    public void validateCheckBox(){

        mHotChkBox=(CheckBox)findViewById(R.id.hot_chkBox);
        mWarmChkBox=(CheckBox)findViewById(R.id.warm_chkBox);
        mColdChkBox=(CheckBox)findViewById(R.id.cold_chkBox);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWarmChkBox.setChecked(true);
            mHotChkBox.setChecked(false);
            mColdChkBox.setChecked(false);
        }



        mHotChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeadStatus = "Hot";
                mHotChkBox.setChecked(true);
                mWarmChkBox.setChecked(false);
                mColdChkBox.setChecked(false);
                mConvertOppButton.setVisibility(View.VISIBLE);
            }
        });
        mWarmChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeadStatus = "Warm";
                mWarmChkBox.setChecked(true);
                mHotChkBox.setChecked(false);
                mColdChkBox.setChecked(false);
                mConvertOppButton.setVisibility(View.GONE);
            }
        });
        mColdChkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeadStatus = "Cold";
                mColdChkBox.setChecked(true);
                mHotChkBox.setChecked(false);
                mWarmChkBox.setChecked(false);
                mConvertOppButton.setVisibility(View.GONE);
            }
        });


    }

    public void validateData() {
        hideKeyboard();


        mCompanyName = mCompanyNameATV.getText().toString();
        mLeadSizeOfCompany = mLeadSizeOfCompanyET.getText().toString();
        mWhatYouNeed = mDetailDescriptipnET.getText().toString();
        mContactName = mContactNameATV.getText().toString();


        if (!validateString(mCompanyName)) {
            mCompanyNameATV.setError("Company name should not be null");
        }  else if (!validateString(mLeadSizeOfCompany)) {
            mLeadSizeOfCompanyET.setError("Lead size field should not be null");
        } else if (!validateString(mWhatYouNeed)) {
            mDetailDescriptipnET.setError("Description should not be null");
        } else if (!validateString(mContactName)) {
            mContactNameATV.setError("Contact should not be null");
        } else if (mLeadStatus == null) {
            Toast.makeText(NewLeadActivity.this, "Lead status not confirm", Toast.LENGTH_SHORT).show();
        }else {
            mCompanyNameATV.setError(null);
            mLeadSizeOfCompanyET.setError(null);
            mDetailDescriptipnET.setError(null);
            mContactNameATV.setError(null);

            Toast.makeText(NewLeadActivity.this, "Lead created", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(NewLeadActivity.this, ManageCompanyContactActivity.class);
            intent.putExtra("companyName", mCompanyNameATV.getText().toString());
            startActivity(intent);

        }
    }

    public boolean validateString(String data) {
        return data.length() > 0;
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
    public void onBackPressed() {
        startActivity( new Intent(this, DashBoardActivity.class) );
        finish();
    }

}
