package com.sfamobile.dahlia.sfamobile.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.AdapterForExpandableListview;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleLeadListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ChildModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.Model.ParentModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 05-08-2016.
 */
public class MeetingsFragment extends Fragment {
    String[] company = {"Dahlia Tech", "Wipro LTD", "IBM LTD", "Microsoft LTD"};


    Activity mActivity = null;


    TextView meetingTitle, client_name, meeting_info, location, date, time, set_remainder;
    // Button edit;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView = null;

    List<ParentModelClassInAddMeetings> listDataHeader = null;
    List<ChildModelClassInAddMeetings> listDataChild = null;
    Context _context = null;
    ParentModelClassInAddMeetings parent1 = null;
    ParentModelClassInAddMeetings parent2 = null;
    ParentModelClassInAddMeetings parent3 = null;
    ChildModelClassInAddMeetings child1 = null;
    ChildModelClassInAddMeetings child2 = null;
    ChildModelClassInAddMeetings child3 = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.meetings_fragment, container, false);


        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        prepareListData();
        // Context _context = null;

        //listDataHeader = new ArrayList<ParentModelClass>();
        // listDataChild = new ArrayList<ChildModelClass>();
        listAdapter = new AdapterForExpandableListview(mActivity, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

//        ListView lv = (ListView)rootView.findViewById(R.id.meetings_lv);
//        ViewMultipleLeadListAdapter viewMultipleLeadListAdapter = new ViewMultipleLeadListAdapter(mActivity,company);
//        lv.setAdapter(viewMultipleLeadListAdapter);

        return rootView;
    }


    private void prepareListData() {
        listDataHeader =  new ArrayList<ParentModelClassInAddMeetings>();
        listDataChild =  new ArrayList<ChildModelClassInAddMeetings>();

        parent1 = new ParentModelClassInAddMeetings();
        parent2 = new ParentModelClassInAddMeetings();
        parent3 = new ParentModelClassInAddMeetings();

        parent1.setMeetingTitle("SFA ");
        parent1.setTime("5pm");
        parent1.setDate("9/8/2016");

        parent2.setMeetingTitle("CRM");
        parent2.setTime("8.15 p.m");
        parent2.setDate("9/8/2016");

        parent3.setMeetingTitle("H.R MEETING");
        parent3.setTime("10pm");
        parent3.setDate("9/8/2016");


        listDataHeader.add( parent1);
        listDataHeader.add( parent2);
        listDataHeader.add( parent3);


        child1 = new ChildModelClassInAddMeetings();
        child1.setClientName("Rahul");
        child1.setMeetingInfo("This meeting is about SFA, stands for sales force automation");
        child1.setLocation("pune");
        child1.setSetRemainder("30 minutes");

        child2 = new ChildModelClassInAddMeetings();
        child2.setClientName("Ram");
        child2.setMeetingInfo("This meeting is about CRM");
        child2.setLocation("Hyderabad");
        child2.setSetRemainder("30 minutes");


        child3 = new ChildModelClassInAddMeetings();
        child3.setClientName("Deepika");
        child3.setMeetingInfo("This meeting is about SFA, stands for sales force automation");
        child3.setLocation("vizag");
        child3.setSetRemainder("30 minutes");

        listDataChild.add(child1);
        listDataChild.add(child2);
        listDataChild.add(child3);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

}
