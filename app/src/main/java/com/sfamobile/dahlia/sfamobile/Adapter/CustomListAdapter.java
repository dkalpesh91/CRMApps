package com.sfamobile.dahlia.sfamobile.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sfamobile.dahlia.sfamobile.Model.Catalog;
import com.sfamobile.dahlia.sfamobile.Model.ChildModel;
import com.sfamobile.dahlia.sfamobile.Model.HeaderChildModel;
import com.sfamobile.dahlia.sfamobile.Model.HeaderModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 01-08-2016.
 */
public class CustomListAdapter extends BaseExpandableListAdapter {
    private static LayoutInflater inflater = null;
    private List<HeaderModel> listDataHeader = null;
    private List<HeaderModel> listDataChild = null;
    private List<HeaderModel> _listDataHeader = null;
    private   List<ChildModel> _listDataChild = null;
    private List<HeaderChildModel> totalCell = null;
    HeaderModel mList = null;
    Catalog mLis = null;
    private Context _context;
    CustomListAdapter adapter = null;

    String mName = null;
    String mRoll = null;
    String mEmail = null;
    String mContact = null;

    public TextView nameSaving;
    public  TextView rollSaving ;
    public  TextView emailSaving;
    public TextView contactSaving ;

    public EditText mUpdateName;
    public  EditText mUpdateRoll;
    public  EditText mUpdateEmail;
    public  EditText mUpdateContact;

    public Button delete;
    public  Button edit ;
    public CustomListAdapter(Context context, List<HeaderModel> listDataHeader,List<ChildModel> listDataChild) {

        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;


        _context = context;

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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child, null);
        }

        rollSaving = (TextView) convertView.findViewById(R.id.roll);
        emailSaving = (TextView) convertView.findViewById(R.id.email);
        contactSaving  = (TextView) convertView.findViewById(R.id.contact);


        ChildModel childObject;

        childObject = _listDataChild.get(groupPosition);


        rollSaving.setText(String.valueOf( childObject.getRollSaving()));
        emailSaving.setText(String.valueOf( childObject.getEmailSaving()));
        contactSaving.setText(String.valueOf( childObject.getContactSaving()));

        KeyListener mKeyListener = nameSaving.getKeyListener();
        nameSaving.setKeyListener(null);
        rollSaving.setKeyListener(null);
        emailSaving.setKeyListener(null);
        contactSaving.setKeyListener(null);

        return convertView;


    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }



    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }



    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


        //  Holder holder = new Holder();
        HeaderModel parentPosition = _listDataHeader.get(groupPosition);
        //  Log.v("TAG", headerTitle.toString());
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            edit =(Button) convertView.findViewById(R.id.edit_button);
            delete =(Button) convertView.findViewById(R.id.delete_button);
        }


        nameSaving = (TextView) convertView .findViewById(R.id.header);


        nameSaving.setTypeface(null, Typeface.BOLD);
        nameSaving.setText(String.valueOf(parentPosition.getHeaderName()));

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(_context);

                dialog.setContentView(R.layout.dialog_for_delete);


                Button cancel;

                cancel= (Button) dialog.findViewById(R.id.cancel_button_delete_dialog);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button yes;
                yes= (Button) dialog.findViewById(R.id.save_button_delete_dialog);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final HeaderModel pmc = _listDataHeader.get(groupPosition);

                        final ChildModel pmc1 = _listDataChild.get(groupPosition);

                        _listDataHeader.remove(pmc);
                        _listDataChild.remove(pmc1);

                        CustomListAdapter.this.notifyDataSetChanged();
                        Log.d("tag", "clicked");

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

        });

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final Dialog dialog = new Dialog(_context);
                dialog.setTitle("Edit contact here"); //Set Alert dialog title here
                // Include dialog.xml file
                // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialog.setCanceledOnTouchOutside(true);

                dialog.setContentView(R.layout.dialog_edit_contacts);

                mUpdateName = (EditText) dialog.findViewById(R.id.editText);
                mUpdateRoll = (EditText)dialog. findViewById(R.id.editText2);
                mUpdateEmail = (EditText) dialog.findViewById(R.id.editText3);
                mUpdateContact = (EditText) dialog.findViewById(R.id.editText4);



                HeaderModel updatingFields1 = new   HeaderModel();
                updatingFields1 = _listDataHeader.get(groupPosition);

                ChildModel updatingFields2 = new ChildModel();
                updatingFields2 = _listDataChild.get(groupPosition);

                ChildModel child = new ChildModel();
                child = _listDataChild.get(groupPosition);

                mName = updatingFields1.getHeaderName();
                mRoll =child.getRollSaving();
                mEmail =child.getEmailSaving();
                mContact =child.getContactSaving();

                mUpdateName.setText( mName.toString());
                mUpdateRoll.setText( mRoll.toString());
                mUpdateEmail.setText( mEmail.toString());
                mUpdateContact.setText( mContact.toString());

                Button cancel;

                cancel= (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button save;
                save= (Button) dialog.findViewById(R.id.save_button);

                final HeaderModel finalUpdatingFields = updatingFields1;
                final ChildModel finalUpdatingFieldsChild = updatingFields2;



                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mName = mUpdateName.getText().toString();
                        mRoll = mUpdateRoll.getText().toString();
                        mEmail = mUpdateEmail.getText().toString();
                        mContact = mUpdateContact.getText().toString();

                        if(!validateFields(mName))
                        {
                            mUpdateName.setError("Name should not be null");
                        }

                        else if(!validateFields(  mRoll))
                        {
                            mUpdateRoll.setError("Roll should not be blank");
                        }else if(!validateFields( mEmail))
                        {
                            mUpdateEmail.setError("Email should not be bull ");

                        }else if(!validateEmail(mEmail))
                        {

                            mUpdateEmail.setError(" invalid mail Id");

                        }


                        else if(!validateFields( mContact))
                        {
                            mUpdateContact.setError("please enter phone number");

                        }

                        else {
                            mUpdateName.setError(null);
                            mUpdateRoll.setError(null);
                            mUpdateEmail.setError(null);
                            mUpdateContact.setError(null);


                            finalUpdatingFields.setHeaderName(mUpdateName.getText().toString());

                            _listDataHeader.set(groupPosition, finalUpdatingFields);


                            finalUpdatingFieldsChild.setRollSaving(mUpdateRoll.getText().toString());
                            finalUpdatingFieldsChild.setEmailSaving(mUpdateEmail.getText().toString());
                            finalUpdatingFieldsChild.setContactSaving(mUpdateContact.getText().toString());


                            notifyDataSetChanged();

                            dialog.dismiss();

                        }
                    }
                });

                dialog.show();
            }

        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int chtruesition) {
        return false;
    }

    public boolean validateFields(String fields)

    {
        return fields.length() > 0;
    }
    public boolean validateEmail(String edtEmail) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(edtEmail);
        return matcher.matches();
    }

   /* class Holder {
       // public TextView nameSaving;
        public EditText nameSaving;
        public  EditText rollSaving ;
        public  EditText emailSaving;
        public EditText contactSaving ;
        public  Button delete;
        public  Button edit;


    }*/

}
