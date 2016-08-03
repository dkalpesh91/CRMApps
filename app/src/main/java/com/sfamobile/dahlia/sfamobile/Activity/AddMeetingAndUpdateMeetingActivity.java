package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingAndUpdateMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private TextView pDisplayDate;
    private ImageButton selectDate;
    private int pYear;
    private int pMonth;
    private int pDay;
    String item = null;

    String mTitle = null;
    String mName = null;
    String mMeetingInformation = null;
    String mLocation = null;
    String mDate = null;
    String mTime = null;
    String mRemainder = null;

    String mMeetingTitle = null;
    String mClientName = null;
    String mMeetingInfo = null;
    String mMeetingLocation = null;
    String mMeetingDate = null;
    String mMeetingTime = null;

    private ImageButton select_time;
    // TimePicker timePicker = null;
    // TextView displayTime = null;
    // Button ok_button = null;

    EditText mEditMeetingET = null;
    EditText mEditClientET = null;
    EditText mEditMeetingInfoET = null;
    EditText mEditLocationET = null;
    EditText mEditDateET = null;
    EditText mEditTimeET = null;
    Button cancel_Button;
    Button accept_Button;
    TextView mDropTitleTV;
    TextView mMeetingTitleTV;


    TextView spinnerItem = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting_and_update_meeting);

        selectDate = (ImageButton) findViewById(R.id.select_date);
        pDisplayDate = (TextView) findViewById(R.id.displayDate);
        select_time = (ImageButton) findViewById(R.id.select_time);
        mDropTitleTV = (TextView) findViewById(R.id.text);
        mMeetingTitleTV = (TextView) findViewById(R.id.headerText);
//for edit text
        pDisplayDate.setVisibility(View.INVISIBLE);


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        mEditMeetingET = (EditText) findViewById(R.id.enter_meeting);
        mEditClientET = (EditText) findViewById(R.id.enter_client_name);
        mEditMeetingInfoET = (EditText) findViewById(R.id.enter_meeting_info);
        mEditLocationET = (EditText) findViewById(R.id.location);
        mEditDateET = (EditText) findViewById(R.id.select_Date);
        mEditTimeET = (EditText) findViewById(R.id.Enter_time);
        mEditTimeET.setOnClickListener(this);
        cancel_Button = (Button) findViewById(R.id.button_cancel);
        accept_Button = (Button) findViewById(R.id.button_accept);

        spinnerItem = (TextView) findViewById(R.id.text);
        spinner.setOnItemSelectedListener(this);
        spinner.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

        List<String> categories = new ArrayList<String>();
        categories.add("Set Remainder");
        categories.add("15 minutes");
        categories.add("30 minutes");
        categories.add("45 minutes");
        categories.add("60 minutes");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_add_meeting, R.id.text, categories);
        spinner.setAdapter(dataAdapter);

        Intent i = getIntent();

        final Bundle bundle = getIntent().getExtras();

        boolean myBool=false;

        if(bundle != null) {


            mTitle = i.getStringExtra("MeetingTitle");
            mDate = i.getStringExtra("Date");
            mMeetingInformation = i.getStringExtra("MeetingInfo");
            mLocation = i.getStringExtra("Location");
            mTime = i.getStringExtra("Time");
            mName = i.getStringExtra("ClientName");
            mRemainder = i.getStringExtra("SpinnerHeader");


            mEditMeetingET.setText(mTitle.toString());
            mEditDateET.setText(mDate.toString());
            mEditTimeET.setText(mTime.toString());
            mEditClientET.setText(mName.toString());
            mEditMeetingInfoET.setText(mMeetingInformation.toString());
            mEditLocationET.setText(mLocation.toString());
            spinner.setSelection(2);

        }
        //spinner.setOnItemSelectedListener(this);
        /** Listener for click event of the button */
        selectDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                pYear = cal.get(Calendar.YEAR);
                pMonth = cal.get(Calendar.MONTH);
                pDay = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(AddMeetingAndUpdateMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        mEditDateET.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, pYear, pMonth, pDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });




        accept_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mMeetingTitle = mEditMeetingET.getText().toString();
                mClientName = mEditClientET.getText().toString();
                mMeetingInfo = mEditMeetingInfoET.getText().toString();
                mMeetingLocation = mEditLocationET.getText().toString();
                mMeetingDate = mEditDateET.getText().toString();
                mMeetingTime = mEditTimeET.getText().toString();

                if (!didValidateFields(mMeetingTitle)) {
                    mEditMeetingET.setError("Title should not be null");
                } else if (!didValidateFields(mClientName)) {
                    mEditClientET.setError("client name should not be blank");
                } else if (!didValidateFields(mMeetingInfo)) {
                    mEditMeetingInfoET.setError("plese add some information");

                } else if (!didValidateFields(mMeetingLocation)) {
                    mEditLocationET.setError("location should not be blank");
                } else if (!didValidateFields(mMeetingDate)) {
                    mEditDateET.setError("date should not be blank");
                } else if (!didValidateFields(mMeetingTime)) {
                    mEditTimeET.setError("time should not be blank");
                } else if (item == "Set Remainder") {

                    Toast.makeText(AddMeetingAndUpdateMeetingActivity.this, " please set remainder time", Toast.LENGTH_LONG).show();
                    // Toast.makeText(AddMeetingAndUpdateMeetingActivity.this, "set remainder is blank", Toast.LENGTH_SHORT).show();

                } else {
                    mEditMeetingET.setError(null);
                    mEditClientET.setError(null);
                    mEditMeetingInfoET.setError(null);
                    mEditLocationET.setError(null);
                    mEditDateET.setError(null);
                    mEditTimeET.setError(null);


                    Toast.makeText(AddMeetingAndUpdateMeetingActivity.this, "saved sucessfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), ShowMeetingActivity.class);
                    startActivity(i);
                    setContentView(R.layout.activity_show_meeting);

                }

            }

            // Log.d("tag",String.valueOf(mTotalProducts.size()));

        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mEditMeetingET.setText("");
                mEditClientET.setText("");
                mEditMeetingInfoET.setText("");
                mEditLocationET.setText("");
                mEditDateET.setText("");
                mEditTimeET.setText("");
                spinner.setSelection(0);

            }
        });

    }

//for spinner

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

        // TODO Auto-generated method stub
    }
//for time picker

    public boolean didValidateFields(String fields)

    {
        return fields.length() > 0;
    }

    @Override
    public void onClick(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddMeetingAndUpdateMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedHour <= 12) {
                    mEditTimeET.setText(selectedHour + ":" + selectedMinute+" AM");
                }else {
                    mEditTimeET.setText(selectedHour + ":" + selectedMinute+" PM");
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}

