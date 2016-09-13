package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Adapter.AdapterForExpandableListview;
import com.sfamobile.dahlia.sfamobile.Model.UpdateMeetingModelClass;
import com.sfamobile.dahlia.sfamobile.Model.ChildModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.Model.ParentModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.R;
import java.util.ArrayList;
import java.util.List;

public class ShowMeetingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView meetingTitle, client_name, meeting_info, location, date, time, set_remainder;
    // Button edit;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    //  ParentModelClass listDataHeader = null;
    // ChildModelClass listDataChild = null;
    List<ParentModelClassInAddMeetings> listDataHeader = null;
    List<ChildModelClassInAddMeetings> listDataChild = null;
    Context _context = null;
    ParentModelClassInAddMeetings parent1 = null;
    ParentModelClassInAddMeetings parent2 = null;
    ParentModelClassInAddMeetings parent3 = null;
    ChildModelClassInAddMeetings child1 = null;
    ChildModelClassInAddMeetings child2 = null;
    ChildModelClassInAddMeetings child3 = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;

    private List<ChildModelClassInAddMeetings> listData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_meeting);


        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        prepareListData();
        // Context _context = null;

        //listDataHeader = new ArrayList<ParentModelClass>();
        // listDataChild = new ArrayList<ChildModelClass>();
        listAdapter = new AdapterForExpandableListview(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("My Meeting");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(this);
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddIMV.setOnClickListener(this);

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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back_arrow_img:

                Intent intent = new Intent(ShowMeetingActivity.this,DashBoardActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.add_item_img:
                Intent intent1 = new Intent(ShowMeetingActivity.this,AddMeetingAndUpdateMeetingActivity.class);
                startActivity(intent1);
                finish();
                break;



            default:
                break;
        }


    }
}

