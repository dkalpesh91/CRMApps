package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.sfamobile.dahlia.sfamobile.Adapter.SpinnerAdapter;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.Calendar;

public class DailySalesVisitReportActivity extends AppCompatActivity implements View.OnClickListener {

    Button mSaveButton = null;

    AutoCompleteTextView mChooseDateATV = null;

    AutoCompleteTextView mPersonNameATV = null;
    AutoCompleteTextView mClientNameATV = null;
    AutoCompleteTextView mStandingLoactionATV = null;
    AutoCompleteTextView mClientLoactionATV = null;

    EditText mNoteDetailsET = null;
    Spinner mClientStatusSP = null;

    String[] company = {"Dahlia", "Wipro", "IBM", "Microsoft"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};
    String[] mClientStatus = {"No Status","Sales call", "Followup", "Negotiation", "cold call","Closure Meeting"};

    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sales_visit_report);
        initView();
    }

    private void initView(){


        mSaveButton = (Button) findViewById(R.id.dsvrc_save_btn);

        mChooseDateATV = (AutoCompleteTextView) findViewById(R.id.dsvrc_select_date_atv);
        mChooseDateATV.setOnClickListener(this);

        mNoteDetailsET = (EditText) findViewById(R.id.dsvrc_note_descriptipn_et);

        mClientStatusSP = (Spinner) findViewById(R.id.dsvrc_status_spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, mClientStatus);
//        mClientStatusSP.setAdapter(adapter);
        SpinnerAdapter adapter=new SpinnerAdapter(this,
                R.layout.spinner_status_item,R.id.spinner_value_tv,mClientStatus);
        mClientStatusSP.setAdapter(adapter);

        mPersonNameATV = (AutoCompleteTextView) findViewById(R.id.dsvrc_person_name_atv);
        ArrayAdapter companyName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contact);
        mPersonNameATV.setAdapter(companyName);
        mPersonNameATV.setThreshold(1);


        mClientNameATV = (AutoCompleteTextView) findViewById(R.id.dsvrc_client_name_atv);
        ArrayAdapter contactName = new ArrayAdapter(this, android.R.layout.simple_list_item_1, company);
        mClientNameATV.setAdapter(contactName);
        mClientNameATV.setThreshold(1);

    }

    @Override
    public void onClick(View v) {
        Calendar mcurrentDate=Calendar.getInstance();
        mYear=mcurrentDate.get(Calendar.YEAR);
        mMonth=mcurrentDate.get(Calendar.MONTH);
        mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(DailySalesVisitReportActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                mChooseDateATV.setText(selectedday+"/"+selectedmonth+"/"+selectedyear);
            }
        },mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }


}
