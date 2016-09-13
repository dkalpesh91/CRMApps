package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ExpandableListAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewCompanyContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ManageCompanyContactModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageCompanyContactActivity extends AppCompatActivity implements View.OnClickListener {



    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    ListView mCompanyLisLV = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD","HCL LTD"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company_contact);



        prepareListData();



        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Contacts");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(this);


        mCompanyLisLV = (ListView)findViewById(R.id.company_contact_lv);

        ViewCompanyContactListAdapter viewMultipleLeadListAdapter = new ViewCompanyContactListAdapter(this,company);
        mCompanyLisLV.setAdapter(viewMultipleLeadListAdapter);

    }

    private void prepareListData() {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(ManageCompanyContactActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.add_item_img:

                Intent intent1 = new Intent(ManageCompanyContactActivity.this,AddNewCompanyContactActivity.class);
                startActivity(intent1);
                finish();

                break;



            default:
                break;
        }
    }
}
