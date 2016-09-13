package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleLeadListAdapter;
import com.sfamobile.dahlia.sfamobile.R;

public class ManageLeadActivity extends AppCompatActivity implements View.OnClickListener {

    ListView mLeadListLV = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lead);
        mLeadListLV = (ListView)findViewById(R.id.aml_lead_details_lv);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Leads");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(this);

        ViewMultipleLeadListAdapter viewMultipleLeadListAdapter = new ViewMultipleLeadListAdapter(this,company);
        mLeadListLV.setAdapter(viewMultipleLeadListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(ManageLeadActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.add_item_img:
                Intent intent1 = new Intent(ManageLeadActivity.this,NewLeadActivity.class);
                startActivity(intent1);
                finish();
                break;



            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, DashBoardActivity.class) );
        finish();
    }

}
