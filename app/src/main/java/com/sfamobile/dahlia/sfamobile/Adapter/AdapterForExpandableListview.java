package com.sfamobile.dahlia.sfamobile.Adapter;

/**
 * Created by Admin on 02-08-2016.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Activity.AddMeetingAndUpdateMeetingActivity;
import com.sfamobile.dahlia.sfamobile.Activity.PlanTravalActivity;
import com.sfamobile.dahlia.sfamobile.Model.ChildModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.Model.ParentModelClassInAddMeetings;
import com.sfamobile.dahlia.sfamobile.R;
import com.sfamobile.dahlia.sfamobile.Model.UpdateMeetingModelClass;


public class AdapterForExpandableListview extends BaseExpandableListAdapter {
    Button edit;
    Button mAddExpense = null;


    private Context _context;
    String childText = null;
    ChildModelClassInAddMeetings child = null;
    private List<ParentModelClassInAddMeetings> listDataHeader = null;
    private   List<ChildModelClassInAddMeetings> listDataChild = null;
    private List<ParentModelClassInAddMeetings> _listDataHeader; // header titles
    // child data in format of header title, child title
    private   List<ChildModelClassInAddMeetings> _listDataChild;
    TextView meetingInfo;
    TextView location;
    TextView date;
    TextView time;
    TextView setRemainder;
    TextView name0fClient;


    public AdapterForExpandableListview(Context context, List<ParentModelClassInAddMeetings> listDataHeader, List<ChildModelClassInAddMeetings> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return _listDataChild;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ChildModelClassInAddMeetings childObject;

        childObject = _listDataChild.get(groupPosition);
        Log.v("TAG", childObject.toString());

        //List childText = (ArrayList) getChild(groupPosition, childPosition);
        //  Log.v("TAG", childText.toString());
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_add_meeting, null);
        }

        name0fClient = (TextView) convertView
                .findViewById(R.id.clientName);
        meetingInfo = (TextView) convertView.findViewById(R.id.meetingInfo);

        location = (TextView) convertView
                .findViewById(R.id.Location);

        setRemainder = (TextView) convertView
                .findViewById(R.id.remainder);


        name0fClient.setText(String.valueOf(childObject.getClientName()));
        meetingInfo.setText(String.valueOf(childObject.getMeetingInfo()));

        location.setText(String.valueOf(childObject.getLocation()));

        setRemainder.setText(String.valueOf(childObject.getSetRemainder()));


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        ParentModelClassInAddMeetings parentPosition = _listDataHeader.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_header_add_meetings, null);
            edit = (Button)convertView.findViewById(R.id.button_edit);
            mAddExpense = (Button)convertView.findViewById(R.id.add_expense);
        }

        mAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(_context, PlanTravalActivity.class);
                _context.startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String item = ((TextView) view).getText().toString();

                Intent i = new Intent(_context, AddMeetingAndUpdateMeetingActivity.class);


                UpdateMeetingModelClass updatingFields = new  UpdateMeetingModelClass();
                ParentModelClassInAddMeetings pmc = _listDataHeader.get(groupPosition);
                updatingFields.setMeetingTitle(pmc.getMeetingTitle());
                updatingFields.setTime(pmc.getTime());
                updatingFields.setDate(pmc.getDate());

                ChildModelClassInAddMeetings pmc1 = _listDataChild.get(groupPosition);

                updatingFields.setClientName(pmc1.getClientName());
                updatingFields.setLocation(pmc1.getLocation());

                updatingFields.setMeetingInfo(pmc1.getMeetingInfo());
                updatingFields.setSetRemainder(pmc1.getSetRemainder());

                i.putExtra("MeetingTitle", updatingFields.getMeetingTitle());
                i.putExtra("ClientName", updatingFields.getClientName());
                i.putExtra("MeetingInfo", updatingFields.getMeetingInfo());
                i.putExtra("Location",updatingFields.getLocation() );
                i.putExtra("Date",updatingFields.getDate() );
                i.putExtra("Time",updatingFields.getTime() );
                i.putExtra("SpinnerHeader",updatingFields.getSetRemainder() );

                _context.startActivity(i);
                ((Activity)_context).finish();
                Log.v("TAG", "CLICKED");
                Log.v("TAG", item.toString());

            }
        });

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listHeader);
        date = (TextView) convertView
                .findViewById(R.id.Date);
        time = (TextView) convertView.findViewById(R.id.Time);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(String.valueOf(parentPosition.getMeetingTitle()));
        date.setText(String.valueOf(parentPosition.getDate()));
        time.setText(String.valueOf(parentPosition.getTime()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
