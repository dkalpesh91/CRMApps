package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Activity.ManageEditContactActivity;
import com.sfamobile.dahlia.sfamobile.Activity.OpportunityEditActivity;
import com.sfamobile.dahlia.sfamobile.R;

/**
 * Created by Admin on 05-08-2016.
 */
public class ViewCompanyContactListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    int i=0;
    String[] idStr ={"1101","1102","1103","1104","1105"};
    /*************  CustomAdapter Constructor *****************/
    public ViewCompanyContactListAdapter(Activity a, String[] d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {


        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView cid;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.managed_opportunity_list_group, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.molg_comapny_name_tv);
            holder.cid = (TextView) vi.findViewById(R.id.molg_comapny_id_tv);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.length<=0)
        {
            holder.name.setText("No Data");


        }
        else
        {
            /***** Get each Model object from Arraylist ********/

            /************  Set Model values in Holder elements ***********/


            holder.name.setText(data[position]);
            holder.cid.setText("Company Id -"+idStr[position]);


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            Intent intent = new Intent(activity,ManageEditContactActivity.class);
            activity.startActivity(intent);
            ((Activity)activity).finish();


        }
    }
}
