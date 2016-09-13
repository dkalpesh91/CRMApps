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
import android.widget.ScrollView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.Catalog;
import com.sfamobile.dahlia.sfamobile.Model.ChildModel;
import com.sfamobile.dahlia.sfamobile.Model.HeaderModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddingContactsActivity extends AppCompatActivity {
    ExpandableListView list = null;
    ExpandableListAdapter listAdapter;
    Catalog product = null;
    Catalog  product1 = null;

    Catalog mList = null;
    List<Catalog> mTotalProducts = null;
    private List<HeaderModel> listDataHeader = null;
    List<ChildModel> listDataChild = null;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contacts);
        mSave_button = (Button) findViewById(R.id.button_save);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Add Meeting");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AddingContactsActivity.this,ShowMeetingActivity.class);
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

        list = (ExpandableListView) findViewById(R.id.expandablelist);

        mEditName2.setVisibility(View.INVISIBLE);
        mEditRole2.setVisibility(View.INVISIBLE);
        mEditEmail2.setVisibility(View.INVISIBLE);
        mEditNumber2.setVisibility(View.INVISIBLE);
        mCountryCode.setVisibility(View.INVISIBLE);
        mSave_button.setVisibility(View.INVISIBLE);

        getPrepareListData();

        mActivityAddIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mEditName2.getVisibility()==View.VISIBLE && mEditName2.getVisibility()==View.VISIBLE && mEditEmail2.getVisibility()==View.VISIBLE && mEditNumber2.getVisibility()==View.VISIBLE  ){

                    mEditName2.setVisibility(View.INVISIBLE);
                    mEditRole2.setVisibility(View.INVISIBLE);
                    mEditEmail2.setVisibility(View.INVISIBLE);
                    mEditNumber2.setVisibility(View.INVISIBLE);
                    mCountryCode.setVisibility(View.INVISIBLE);
                    mSave_button.setVisibility(View.INVISIBLE);

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
                else if(!didValidateFields(sCountrycode))
                {
                    mCountryCode.setError("country code should not be blank");
                }

                else
                {
                    mEditName2.setError(null);

                    mEditRole2.setError(null);
                    mEditEmail2.setError(null);
                    mEditNumber2.setError(null);

                    //  product  = new  Catalog();
                    HeaderModel titlePosition = new HeaderModel();
                    titlePosition.setHeaderName(mEditName2.getText().toString());

                    ChildModel childPosition = new ChildModel();

                    childPosition.setRollSaving(mEditRole2.getText().toString());
                    childPosition.setEmailSaving(mEditEmail2.getText().toString());
                    childPosition.setContactSaving(mEditNumber2.getText().toString());

                    listDataHeader.add(titlePosition);
                    listDataChild.add(childPosition);

                    mEditName2.setVisibility(View.INVISIBLE);
                    mEditRole2.setVisibility(View.INVISIBLE);
                    mEditEmail2.setVisibility(View.INVISIBLE);
                    mEditNumber2.setVisibility(View.INVISIBLE);
                    mCountryCode.setVisibility(View.INVISIBLE);
                    mSave_button.setVisibility(View.INVISIBLE);


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


    private void getPrepareListData() {
        listDataHeader =  new ArrayList<HeaderModel>();
        listDataChild =  new ArrayList<ChildModel>();

        HeaderModel firstObject1 = new HeaderModel();
        HeaderModel firstObject2 = new HeaderModel();
        HeaderModel firstObject3 = new HeaderModel();

        firstObject1.setHeaderName("Ram");
        firstObject2.setHeaderName("Ravi");
        firstObject3.setHeaderName("kamala");

        listDataHeader.add(firstObject1);
        listDataHeader.add(firstObject2);
        listDataHeader.add(firstObject3);


        ChildModel secondObject1 = new ChildModel();
        secondObject1.setRollSaving("Team lead");
        secondObject1.setContactSaving("8896689898");
        secondObject1.setEmailSaving("janu@gmail.com");
        listDataChild.add(secondObject1);

        ChildModel secondObject2 = new ChildModel();

        secondObject2.setRollSaving("supervisor");
        secondObject2.setContactSaving("8896689898");
        secondObject2.setEmailSaving("ravi@gmail.com");
        listDataChild.add(secondObject2);

        ChildModel secondObject3 = new ChildModel();
        secondObject3.setRollSaving("supervisor");
        secondObject3.setContactSaving("8896689898");
        secondObject3.setEmailSaving("kamala@gmail.com");
        listDataChild.add(secondObject3 );


    }
}
