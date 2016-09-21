package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.Catalog;
import com.sfamobile.dahlia.sfamobile.Model.ChildModel;
import com.sfamobile.dahlia.sfamobile.Model.ContactPersonModel;
import com.sfamobile.dahlia.sfamobile.Model.HeaderModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddingContactsActivity extends AppCompatActivity {
    ListView list = null;
    ExpandableListAdapter listAdapter;
    Catalog product = null;
    Catalog  product1 = null;



    EditText mEditName2 = null;
    EditText mEditRole2 = null;
    EditText mEditEmail2 = null;
    EditText mEditNumber2 = null;
    EditText mCountryCode = null;
    TextView mNameSaving = null;
    TextView mRollSaving = null;
    TextView mEmailSaving = null;
    TextView mContactSaving = null;

    private Button mSave_button = null;
    private  Button mDelete_button = null;
    private  Button mEdit_button =null;
    ScrollView mScroll_button = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    private ArrayList<ContactPersonModel> contactlist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contacts);
        mSave_button = (Button) findViewById(R.id.button_save);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Add Contact");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AddingContactsActivity.this,NewLeadActivity.class);
                startActivity(intent);
                finish();



            }
        });
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);

        mEditName2 = (EditText) findViewById(R.id.edit_Username2);
        mEditRole2 = (EditText) findViewById(R.id.edit_Role2);
        mEditEmail2 = (EditText) findViewById(R.id.edit_Email2);
        mEditNumber2 = (EditText) findViewById(R.id.edit_Number2);
        mCountryCode = (EditText) findViewById(R.id.code);

        list = (ListView) findViewById(R.id.expandablelist);

//        mEditName2.setVisibility(View.INVISIBLE);
//        mEditRole2.setVisibility(View.INVISIBLE);
//        mEditEmail2.setVisibility(View.INVISIBLE);
//        mEditNumber2.setVisibility(View.INVISIBLE);
//        mCountryCode.setVisibility(View.INVISIBLE);
//        mSave_button.setVisibility(View.INVISIBLE);

        getContactData();
        final ViewMultipleContactListAdapter adapter =new ViewMultipleContactListAdapter(this, contactlist);
        list.setAdapter(adapter);

        mActivityAddIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mEditName2.getVisibility()==View.VISIBLE && mEditName2.getVisibility()==View.VISIBLE && mEditEmail2.getVisibility()==View.VISIBLE && mEditNumber2.getVisibility()==View.VISIBLE  ){

                    mEditName2.setVisibility(View.GONE);
                    mEditRole2.setVisibility(View.GONE);
                    mEditEmail2.setVisibility(View.GONE);
                    mEditNumber2.setVisibility(View.GONE);
                    mCountryCode.setVisibility(View.GONE);
                    mSave_button.setVisibility(View.GONE);

                }else{


                    mEditName2.setVisibility(View.VISIBLE);
                    mEditRole2.setVisibility(View.VISIBLE);
                    mEditEmail2.setVisibility(View.VISIBLE);
                    mEditNumber2.setVisibility(View.VISIBLE);
                    mCountryCode.setVisibility(View.VISIBLE);
                    mSave_button.setVisibility(View.VISIBLE);

                    mEditName2.setText("");
                    mEditRole2.setText("");
                    mEditEmail2.setText("");
                    mEditNumber2.setText("");
                    mCountryCode.setText("");

                }

            }
        });





        mSave_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String sFirstName = mEditName2.getText().toString();
                String sRole = mEditRole2.getText().toString();
                String sEmailId = mEditEmail2.getText().toString();
                String sPhoneNo = mEditNumber2.getText().toString();
                String sCountrycode = mCountryCode.getText().toString();

                if(!didValidateFields(sFirstName))
                {
                    mEditName2.setError("username name should not be null");
                }

                else if(!didValidateFields(sEmailId))
                {
                    mEditEmail2.setError("email should not be blank");
                }else if(!validateEmail(sEmailId))
                {
                    mEditEmail2.setError("valid invalid mail Id");

                }

                else if(!didValidateFields(sPhoneNo))
                {
                    mEditNumber2.setError("mobile number  should not be blank");
                }
                else if(!didValidateFields(sRole))
                {
                    mEditRole2.setError("company name should not be blank");
                }
//                else if(!didValidateFields(sCountrycode))
//                {
//                    mCountryCode.setError("country code should not be blank");
//                }

                else
                {
                    mEditName2.setError(null);

                    mEditRole2.setError(null);
                    mEditEmail2.setError(null);
                    mEditNumber2.setError(null);

                    ContactPersonModel childPosition = new ContactPersonModel();
                    childPosition.setName(mEditName2.getText().toString());
                    childPosition.setRole(mEditRole2.getText().toString());
                    childPosition.setEmailId(mEditEmail2.getText().toString());
                    childPosition.setPhoneNumber(mEditNumber2.getText().toString());

                    contactlist.add(childPosition);

                    adapter.notifyDataSetChanged();

                    mEditName2.setVisibility(View.GONE);
                    mEditRole2.setVisibility(View.GONE);
                    mEditEmail2.setVisibility(View.GONE);
                    mEditNumber2.setVisibility(View.GONE);
                    mCountryCode.setVisibility(View.GONE);
                    mSave_button.setVisibility(View.GONE);


                    //  Toast.makeText(RegisterActivity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }
    public boolean didValidateFields(String fields)

    {
        return fields.length() > 0;
    }

    public boolean validateEmail(String edtEmail) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(edtEmail);
        return matcher.matches();
    }


    private void getContactData(){
        contactlist = new ArrayList<ContactPersonModel>();

        ContactPersonModel contactPersonModel = new ContactPersonModel();

        contactPersonModel.setName("Ravi V");
        contactPersonModel.setEmailId("ravi@gmail.com");
        contactPersonModel.setPhoneNumber("9292929292");
        contactPersonModel.setRole("CEO");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Naveen G");
        contactPersonModel.setEmailId("Naveen@gmail.com");
        contactPersonModel.setPhoneNumber("9696969696");
        contactPersonModel.setRole("Area Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Rahul");
        contactPersonModel.setEmailId("rahul@gmail.com");
        contactPersonModel.setPhoneNumber("9898998989");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Viraj");
        contactPersonModel.setEmailId("viraj@yahoo.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Sales Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Vishal");
        contactPersonModel.setEmailId("vishal@gmail.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);



    }
}
