package com.sfamobile.dahlia.sfamobile.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleLeadListAdapter;
import com.sfamobile.dahlia.sfamobile.R;

public class ManageLeadActivity extends AppCompatActivity {

    ListView mLeadListLV = null;

    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_lead);
        mLeadListLV = (ListView)findViewById(R.id.aml_lead_details_lv);
        ViewMultipleLeadListAdapter viewMultipleLeadListAdapter = new ViewMultipleLeadListAdapter(this,company);
        mLeadListLV.setAdapter(viewMultipleLeadListAdapter);
    }
}
