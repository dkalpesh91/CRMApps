package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.SpinnerAdapter;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ConvertLeadToOpportunityActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {




    Button mSaveButton = null;
    Button mAddProductButton = null;

    AutoCompleteTextView mChooseDateATV = null;

    AutoCompleteTextView mPersonNameATV = null;
    AutoCompleteTextView mClientNameATV = null;
    AutoCompleteTextView mAmountATV = null;

    Button mSelectClosureDate;



    TextInputLayout mreasonTIL = null;

    EditText mDetailedRequirementET = null;
    EditText mreasonET = null;

    Spinner mClientStatusSP = null;

    TextView mActivityNameTV = null;
    TextView mOpportunityStageValue = null;
    ImageView mActivityBackIMV = null;

    String[] company = {"Dahlia", "Wipro", "IBM", "Microsoft"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};
    String[] mClientStatus = {"No Status","Active","Won", "Lost", "Deferred", "On Hold"};

    int mYear,mMonth,mDay;
    boolean isFromNewLead = false;
    boolean isFromLeadEdit = false;

    EditText mCompanyAddressET = null;
    EditText mCompanyEmailET = null;
    EditText mCompanyPhoneET = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_lead_to_opportunity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                isFromNewLead = bundle.getBoolean("isFromNewLead");

            } catch (Exception e) {
                isFromNewLead = false;
            }

            try {
                isFromLeadEdit = bundle.getBoolean("isFromLeadEdit");
            } catch (Exception e) {
                isFromLeadEdit = false;
            }

        }
        initView();

//        ColorSeekBar colorSeekBar = (ColorSeekBar) findViewById(R.id.colorSlider);
//        colorSeekBar.setMaxValue(100);
//        colorSeekBar.setColors(R.array.material_colors); // material_colors is defalut included in res/color,just use it.
//        colorSeekBar.setColorBarValue(10); //0 - maxValue
//        colorSeekBar.setAlphaBarValue(10); //0-255
//        colorSeekBar.setShowAlphaBar(true);
//        colorSeekBar.setBarHeight(5); //5dpi
//        colorSeekBar.setThumbHeight(30); //30dpi
//        colorSeekBar.setBarMargin(10);
//
//        final MarkerSeekBar bar2 = (MarkerSeekBar) findViewById(R.id.markerseekbar);
//        assert bar2 != null;
//        ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(),
//                ContextCompat.getColor(this, R.color.colorAccent),
//                Color.WHITE - ContextCompat.getColor(this, R.color.colorAccent)
//        );
//        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.setInterpolator(new LinearInterpolator());
//        colorAnim.setDuration(1000);
//        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                bar2.getMarkerView().setMarkerColor((int) animation.getAnimatedValue());
//            }
//        });
//        colorAnim.start();

//        seekbar = ((CustomSeekBar) findViewById(R.id.customSeekBar));
//        initDataToSeekbar();

//        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
//            @Override
//            public void onColorChangeListener(int colorBarValue, int alphaBarValue, int color) {
//                Toast.makeText(ConvertLeadToOpportunityActivity.this," "+color,Toast.LENGTH_LONG).show();
//            }
//        });


    }

    private void initView(){


        mSaveButton = (Button) findViewById(R.id.clto_save_btn);
        mSaveButton.setOnClickListener(this);

        mAddProductButton = (Button) findViewById(R.id.add_product_button);
        mAddProductButton.setOnClickListener(this);

        mreasonTIL = (TextInputLayout) findViewById(R.id.clto_reason_til);


        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Opportunity");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);

        mCompanyAddressET = (EditText) findViewById(R.id.clto_client_location_et);
        mCompanyEmailET = (EditText) findViewById(R.id.clto_email_id_et);
        mCompanyPhoneET = (EditText) findViewById(R.id.clto_phone_no_et);
        mAmountATV = (AutoCompleteTextView) findViewById(R.id.clto_amount_atv);

        mOpportunityStageValue = (TextView)findViewById(R.id.clto_opportunity_stage_value_tv);




        mChooseDateATV = (AutoCompleteTextView) findViewById(R.id.clto_select_date_atv);
        mChooseDateATV.setText(setAutoDate());


        mDetailedRequirementET = (EditText) findViewById(R.id.clto_detailed_requirement_et);

        mreasonET = (EditText) findViewById(R.id.clto_reason_et);


        mClientStatusSP = (Spinner) findViewById(R.id.clto_status_spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, mClientStatus);
//        mClientStatusSP.setAdapter(adapter);
        SpinnerAdapter adapter=new SpinnerAdapter(this,
                R.layout.spinner_status_item,R.id.spinner_value_tv,mClientStatus);
        mClientStatusSP.setAdapter(adapter);
        mClientStatusSP.setOnItemSelectedListener(this);

        mPersonNameATV = (AutoCompleteTextView) findViewById(R.id.clto_person_name_atv);

        ArrayAdapter companyName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contact);
        mPersonNameATV.setAdapter(companyName);
        mPersonNameATV.setThreshold(1);


        mClientNameATV = (AutoCompleteTextView) findViewById(R.id.clto_client_name_atv);

        ArrayAdapter contactName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, company);
        mClientNameATV.setAdapter(contactName);
        mClientNameATV.setThreshold(1);

        mSelectClosureDate = (Button)findViewById(R.id.clto_select_date_button);
        mSelectClosureDate.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (isFromLeadEdit == true) {
            mActivityNameTV.setText("Edit Opportunity");

            mClientNameATV.setText(bundle.getString("clientName").toString());
            mCompanyAddressET.setText(bundle.getString("clientEmail").toString());
            mCompanyEmailET.setText(bundle.getString("clientPhone").toString());
            mCompanyPhoneET.setText(bundle.getString("clientAddress").toString());

            mPersonNameATV.setText(bundle.getString("contactName").toString());
            mDetailedRequirementET.setText(bundle.getString("requirement").toString());
            mOpportunityStageValue.setText(bundle.getString("OpportunityStage").toString());
            mAmountATV.setText(bundle.getString("amount").toString());


            mChooseDateATV.setText(bundle.getString("ClosureDate").toString());
            String statusType  = bundle.getString("StatusType").toString();
            mClientStatusSP.setSelection(2);
            mreasonET.setText(bundle.getString("Reason").toString());

        }

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.clto_select_date_button:
                Calendar mcurrentDate=Calendar.getInstance();
                mYear=mcurrentDate.get(Calendar.YEAR);
                mMonth=mcurrentDate.get(Calendar.MONTH);
                mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(ConvertLeadToOpportunityActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        mChooseDateATV.setText(selectedday+"/"+selectedmonth+"/"+selectedyear);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
                break;

            case R.id.back_arrow_img:
                if (isFromNewLead) {
                    Intent intent2 = new Intent(ConvertLeadToOpportunityActivity.this, NewLeadActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Intent intent = new Intent(ConvertLeadToOpportunityActivity.this,ManagedOpportunityActivity.class);
                    startActivity(intent);
                    finish();
                }

                finish();
                break;

            case R.id.clto_save_btn:
                Intent intent2 = new Intent(ConvertLeadToOpportunityActivity.this, ManagedOpportunityActivity.class);
                startActivity(intent2);
                finish();

                finish();
                break;



            case R.id.add_product_button:
                Intent intent3 = new Intent(ConvertLeadToOpportunityActivity.this, ProductCatalogActivity.class);
                intent3.putExtra("isAddProduct",true);
                startActivity(intent3);
                finish();


                finish();
                break;

            default:
                break;
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(ConvertLeadToOpportunityActivity.this, "The planet is " +
//                parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        if (parent.getItemAtPosition(position).toString() == "No Status" ||
                parent.getItemAtPosition(position).toString() == "Active" ||
                parent.getItemAtPosition(position).toString() == "Won"){
            mreasonTIL.setVisibility(View.GONE);
        }else {
            mreasonTIL.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private String setAutoDate(){
        String dateStr = "";
        Calendar mcurrentDate=Calendar.getInstance();
        mYear=mcurrentDate.get(Calendar.YEAR);
        mMonth=mcurrentDate.get(Calendar.MONTH);
        mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        dateStr = mDay+"/"+mMonth+"/"+mYear;
        return dateStr;
    }

}
