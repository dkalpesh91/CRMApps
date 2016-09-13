package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleOpportunityListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.OpportunityModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

public class ManagedOpportunityActivity extends AppCompatActivity implements View.OnClickListener {

    ListView mLeadListLV = null;

    ArrayList<OpportunityModel> mData = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD","HCL LTD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managed_opportunity);
        mLeadListLV = (ListView)findViewById(R.id.amo_opportunity_details_lv);
        ViewMultipleOpportunityListAdapter viewMultipleLeadListAdapter = new ViewMultipleOpportunityListAdapter(this,company);
        mLeadListLV.setAdapter(viewMultipleLeadListAdapter);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Opportunity");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);



    }

    private void setData(){

        mData = new ArrayList<OpportunityModel>();

        OpportunityModel opportunityModel = new OpportunityModel();
        opportunityModel.setCompanyName("Dahlia Tech");
        opportunityModel.setCompanyEmailId("sales@dahlia-tech.com");
        opportunityModel.setCompanyPhoneNumber("+91-9503636103");
        opportunityModel.setCompanyAddress("Amenora Chember, Pune");
        opportunityModel.setContactName("Ravi V");
        opportunityModel.setDetailedRequirement("This is opportunity related to sales, Please follow up for further details");
        opportunityModel.setAmount("1,00,000");
        opportunityModel.setClosureDate("3/7/2016");
        opportunityModel.setStatusType("Won 80%");
        opportunityModel.setReason("blank");
        mData.add(opportunityModel);



        OpportunityModel opportunityModel1 = new OpportunityModel();
        opportunityModel1.setCompanyName("IBM PVT LTD");
        opportunityModel1.setCompanyEmailId("sales@ibm.com");
        opportunityModel1.setCompanyPhoneNumber("+91-9503636109");
        opportunityModel1.setCompanyAddress("Phase 2, Pune");
        opportunityModel1.setContactName("Vishal V");
        opportunityModel1.setDetailedRequirement("This is opportunity related to sales, Please follow up for further details");
        opportunityModel1.setAmount("10,00,000");
        opportunityModel1.setClosureDate("3/7/2016");
        opportunityModel1.setStatusType("Lost 20%");
        opportunityModel1.setReason("Product details are not match");
        mData.add(opportunityModel1);




        OpportunityModel opportunityModel2 = new OpportunityModel();
        opportunityModel2.setCompanyName("HCL PVT LTD");
        opportunityModel2.setCompanyEmailId("sales@hcl-pvt.com");
        opportunityModel2.setCompanyPhoneNumber("+91-9503636111");
        opportunityModel2.setCompanyAddress("Phase1, Pune");
        opportunityModel2.setContactName("Viraj J");
        opportunityModel2.setDetailedRequirement("This is opportunity related to sales, Please follow up for further details");
        opportunityModel2.setAmount("11,00,000");
        opportunityModel2.setClosureDate("3/7/2016");
        opportunityModel2.setStatusType("Won");
        opportunityModel2.setReason("blank");
        mData.add(opportunityModel2);




        OpportunityModel opportunityModel3 = new OpportunityModel();
        opportunityModel3.setCompanyName("3DPLM PVT LTD");
        opportunityModel3.setCompanyEmailId("sales@3dplm-tech.com");
        opportunityModel3.setCompanyPhoneNumber("+91-9503636110");
        opportunityModel3.setCompanyAddress("Phase 2, Pune");
        opportunityModel3.setContactName("Vikas V");
        opportunityModel3.setDetailedRequirement("This is opportunity related to sales, Please follow up for further details");
        opportunityModel3.setAmount("15,00,000");
        opportunityModel3.setClosureDate("3/7/2016");
        opportunityModel1.setStatusType("Lost 10%");
        opportunityModel1.setReason("Product details are not match");
        mData.add(opportunityModel3);




        OpportunityModel opportunityModel4 = new OpportunityModel();
        opportunityModel4.setCompanyName("DTD Solutions LTD");
        opportunityModel4.setCompanyEmailId("sales@dtd-tech.com");
        opportunityModel4.setCompanyPhoneNumber("+91-9103636106");
        opportunityModel4.setCompanyAddress("Info City, Pune");
        opportunityModel4.setContactName("Vishal M");
        opportunityModel4.setDetailedRequirement("This is opportunity related to sales, Please follow up for further details");
        opportunityModel4.setAmount("19,00,000");
        opportunityModel4.setClosureDate("3/7/2016");
        opportunityModel4.setStatusType("Won");
        opportunityModel4.setReason("blank");
        mData.add(opportunityModel4);






    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(ManagedOpportunityActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
                break;



            default:
                break;
        }
    }
}
