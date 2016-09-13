package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.R;

public class LeadDetalisShowActivity extends AppCompatActivity implements View.OnClickListener {


    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityEditIMV = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_detalis_show);


        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Lead Details");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityEditIMV = (ImageView) findViewById(R.id.edit_item_img);
        mActivityEditIMV.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(LeadDetalisShowActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.edit_item_img:
                Intent intent1 = new Intent(LeadDetalisShowActivity.this,NewLeadActivity.class);
                intent1.putExtra("clientName","Dahlia Tech");
                intent1.putExtra("ContactName","Ravi V");
                intent1.putExtra("leadSize","1,00,000");
                intent1.putExtra("desc","Sales leads are generated on the basis of demographic criteria such as FICO score, income, age, HHI, psychographic, etc. These leads are resold to multiple advertisers. Sales leads are typically followed up through phone calls by the sales force. Sales leads are commonly found in the mortgage, insurance and finance leads.");
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
