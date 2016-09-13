package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleQuotationListAdapter;
import com.sfamobile.dahlia.sfamobile.R;

public class ManagedQuotationActivity extends AppCompatActivity implements View.OnClickListener {

    ListView mLeadListLV = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD","HCL LTD"};

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_quotation);
        mLeadListLV = (ListView)findViewById(R.id.amq_quotation_details_lv);
        ViewMultipleQuotationListAdapter viewMultipleLeadListAdapter = new ViewMultipleQuotationListAdapter(this,company);
        mLeadListLV.setAdapter(viewMultipleLeadListAdapter);
        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Quotation");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(ManagedQuotationActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.add_item_img:
                Intent intent1 = new Intent(ManagedQuotationActivity.this,ProductCatalogActivity.class);
                startActivity(intent1);
                finish();
                break;



            default:
                break;
        }
    }
}
