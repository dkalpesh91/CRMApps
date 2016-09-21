package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.R;

public class ManagedExpenseActivity extends AppCompatActivity {


    private RelativeLayout mDailyExpenseLayout = null;
    private RelativeLayout mMonthlyExpenseLayout = null;
    private RelativeLayout mWeeklyExpenseLayout = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_expense);


        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Expense");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagedExpenseActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mDailyExpenseLayout = (RelativeLayout) findViewById(R.id.daily_expense_rl);
        mDailyExpenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagedExpenseActivity.this,AllExpenseManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mWeeklyExpenseLayout = (RelativeLayout) findViewById(R.id.weekly_expense_rl);
        mWeeklyExpenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagedExpenseActivity.this,AllExpenseManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mMonthlyExpenseLayout = (RelativeLayout) findViewById(R.id.monthly_expense_rl);
        mMonthlyExpenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagedExpenseActivity.this,AllExpenseManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
