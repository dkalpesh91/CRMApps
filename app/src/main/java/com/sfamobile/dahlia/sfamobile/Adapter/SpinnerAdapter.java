package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.R;

/**
 * Created by Admin on 27-07-2016.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {
    int groupid;
    Activity context;
    String[] list;
    LayoutInflater inflater;
    public SpinnerAdapter(Activity context, int groupid, int id, String[]
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        TextView textView=(TextView)itemView.findViewById(R.id.spinner_value_tv);
        textView.setText(list[position]);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}
