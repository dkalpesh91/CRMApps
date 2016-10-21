package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewQuotationAdapter;
import com.sfamobile.dahlia.sfamobile.R;

public class ExpenseOutstationActivity extends AppCompatActivity {


    private ImageView mTPAddIV = null;
    private ImageView mLPAddIV = null;
    private ImageView mBPAddIV = null;
    private ImageView mMPAddIV = null;
    private ImageView mPhoneExpenseAddIV = null;

    private Context mContext = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = ExpenseOutstationActivity.this;
        setContentView(R.layout.activity_expense_outstation);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Outstaion");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ManagedExpenseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mTPAddIV = (ImageView) findViewById(R.id.tp_add_iv);
        mTPAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlanTravalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mLPAddIV = (ImageView) findViewById(R.id.lp_add_iv);
        mLPAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlanTravalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mBPAddIV = (ImageView) findViewById(R.id.bp_add_iv);
        mBPAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlanTravalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mMPAddIV = (ImageView) findViewById(R.id.mp_add_iv);
        mMPAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlanTravalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mPhoneExpenseAddIV = (ImageView) findViewById(R.id.phone_expens_add_iv);
        mPhoneExpenseAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                // Include dialog.xml file
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_add_phone_expenses);


                dialog.show();
            }
        });
    }
}
