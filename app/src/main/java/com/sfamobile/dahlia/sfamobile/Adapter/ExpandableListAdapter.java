package com.sfamobile.dahlia.sfamobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.ManageCompanyContactModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 27-07-2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ManageCompanyContactModel>> _listDataChild;

    List<ManageCompanyContactModel> list = null;

    String[] id ={"1101","1102","1103","1104","1105"};

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<ManageCompanyContactModel>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        list= _listDataChild.get("companyDetails");
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        //  return this._listDataChild.get(this._listDataHeader.get(groupPosition))
        //         .get(childPosititon);
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

//        List<ManageCompanyContactModel> list = _listDataChild.get("companyDetails");
//        ManageCompanyContactModel manageCompanyContactModel = list.get(0);
//
//        final String companyEmail = manageCompanyContactModel.getCompanyEmail();
//        final String companyPhone = manageCompanyContactModel.getCompanyPhone();
//        final String companyAddresss = manageCompanyContactModel.getCompanyAddress();
//
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this._context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.company_details_list, null);
//        }
//
//        TextView companyEmailTV = (TextView) convertView
//                .findViewById(R.id.comapny_email_tv);
//        TextView contactPhoneTV = (TextView) convertView
//                .findViewById(R.id.comapny_phone_tv);
//        TextView contactAddresssTV = (TextView) convertView
//                .findViewById(R.id.company_address_tv);
//
//        TextView primaryContactTV = (TextView) convertView
//                .findViewById(R.id.primary_contact_tv);
//
//        TextView secondaryContactTV = (TextView) convertView
//                .findViewById(R.id.secondary_contact_tv);
//
//        secondaryContactTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        companyEmailTV.setText(companyEmail);
//        contactPhoneTV.setText(companyPhone);
//        contactAddresssTV.setText(companyAddresss);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .size();

        return 1;

    }

    @Override
    public Object getGroup(int groupPosition) {
//        return this._listDataHeader.get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {
        //return this._listDataHeader.size();
        return list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        // List<ManageCompanyContactModel> list = _listDataChild.get("companyDetails");
        ManageCompanyContactModel manageCompanyContactModel = list.get(groupPosition);

        final String companyName = manageCompanyContactModel.getCompanyName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.company_details_row, null);
        }

        TextView companyNameTV = (TextView) convertView
                .findViewById(R.id.comapny_name_tv);
        TextView companyIdTV = (TextView) convertView
                .findViewById(R.id.comapny_id_tv);

        companyNameTV.setText(companyName);
        companyIdTV.setText("ID - "+id[groupPosition]);

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