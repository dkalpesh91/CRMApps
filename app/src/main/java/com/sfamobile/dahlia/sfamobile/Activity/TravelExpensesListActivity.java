package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.TravelExpenseListAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewCompanyContactListAdapter;
import com.sfamobile.dahlia.sfamobile.R;

public class TravelExpensesListActivity extends AppCompatActivity {

    private ListView mCompanyLisLV = null;
    private String[] company = null;
    private TextView mActivityNameTV;
    private ImageView mActivityBackIMV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expenses_list);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Travel Expense");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelExpensesListActivity.this,ManagedExpenseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mCompanyLisLV = (ListView)findViewById(R.id.travel_expense_list);

        TravelExpenseListAdapter viewMultipleLeadListAdapter = new TravelExpenseListAdapter(this,company);
        mCompanyLisLV.setAdapter(viewMultipleLeadListAdapter);

    }
}
