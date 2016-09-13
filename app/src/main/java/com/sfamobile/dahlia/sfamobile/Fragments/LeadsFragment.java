package com.sfamobile.dahlia.sfamobile.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleLeadListAdapter;
import com.sfamobile.dahlia.sfamobile.R;

/**
 * Created by Admin on 05-08-2016.
 */
public class LeadsFragment extends Fragment {
    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD"};


    Activity mActivity = null;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.leads_fragment, container, false);
        ListView lv = (ListView)rootView.findViewById(R.id.leads_lv);
        ViewMultipleLeadListAdapter viewMultipleLeadListAdapter = new ViewMultipleLeadListAdapter(mActivity,company);
        lv.setAdapter(viewMultipleLeadListAdapter);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


}
