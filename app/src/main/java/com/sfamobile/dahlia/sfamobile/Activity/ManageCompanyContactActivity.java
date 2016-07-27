package com.sfamobile.dahlia.sfamobile.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.sfamobile.dahlia.sfamobile.Adapter.ExpandableListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ManageCompanyContactModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageCompanyContactActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter = null;
    ExpandableListView expListView = null;
    List<String> listDataHeader = null;
    HashMap<String, List<ManageCompanyContactModel>> listDataChild = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD","HCL LTD"};
    String[] contact = {"Ravi", "Vishal", "Rahul", "Amit"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_company_contact);

        expListView = (ExpandableListView) findViewById(R.id.company_contact_elv);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ManageCompanyContactModel>>();

        String companyName = null;
        String companyEmail = null;
        String companyPhone = null;
        String companyAddress = null;

//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            companyName= extras.getString("companyName");
//            companyEmail = extras.getString("companyEmail");
//            companyPhone = extras.getString("companyPhone");
//            companyAddress = extras.getString("companyAddress");
//        }


        for (int i = 0;i<4;i++){

        }

        // Adding child data
        listDataHeader.add("companyDetails");

        // Adding child data
        List<ManageCompanyContactModel> companyDetails = new ArrayList<ManageCompanyContactModel>();
        //ManageCompanyContactModel manageCompanyContactModel = new ManageCompanyContactModel();
        for (int i = 0;i<contact.length;i++){
            companyName = company[i];
            ManageCompanyContactModel manageCompanyContactModel = new ManageCompanyContactModel();
            manageCompanyContactModel.setCompanyName(companyName);
            companyDetails.add(i,manageCompanyContactModel);
        }
//        manageCompanyContactModel.setCompanyName(companyName);
//        manageCompanyContactModel.setCompanyEmail(companyEmail);
//        manageCompanyContactModel.setCompanyPhone(companyPhone);
//        manageCompanyContactModel.setCompanyAddress(companyAddress);

//        companyDetails.add(manageCompanyContactModel);

        listDataChild.put("companyDetails", companyDetails);
    }

}
