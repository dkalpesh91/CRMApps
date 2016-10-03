package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.ContactPersonModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

/**
 * Created by Admin on 27-07-2016.
 */
public class ViewMultipleContactListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Context activity;
    private ArrayList<ContactPersonModel> data;
    private static LayoutInflater inflater=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public ViewMultipleContactListAdapter(Activity a, ArrayList<ContactPersonModel> d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
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
        public TextView staticName;
        public TextView emailId;
        public TextView phoneNumber;
        public TextView role;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.show_contact_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.staticName = (TextView) vi.findViewById(R.id.dialog_contact_person_name_tv);
            holder.name = (TextView) vi.findViewById(R.id.dialog_contact_person_name_value_tv);
            holder.emailId=(TextView)vi.findViewById(R.id.dialog_contact_person_phone_email_id_value_tv);
            holder.phoneNumber = (TextView) vi.findViewById(R.id.dialog_contact_person_phone_number_value_tv);
            holder.role=(TextView)vi.findViewById(R.id.dialog_contact_person_phone_roll_value_tv);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/

            /************  Set Model values in Holder elements ***********/

            ContactPersonModel contactPersonModel = data.get(position);
            if(position==0){
                holder.staticName.setText("Contact Person - Primary");
            }
            holder.name.setText( contactPersonModel.getName() );
            holder.emailId.setText( contactPersonModel.getEmailId());
            holder.phoneNumber.setText(contactPersonModel.getPhoneNumber());
            holder.role.setText( contactPersonModel.getRole());


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
            //((Activity)activity).finish();

        }
    }
}



