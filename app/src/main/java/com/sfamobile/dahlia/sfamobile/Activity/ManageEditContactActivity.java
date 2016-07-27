package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.ContactPersonModel;
import com.sfamobile.dahlia.sfamobile.R;

import java.util.ArrayList;

public class ManageEditContactActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mShowContactBtn = null;
    private Button mDialogCloseBtn = null;
    private ListView mShowContactLV = null;

    private ArrayList<ContactPersonModel> contactlist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_edit_contact);

        initView();
        getContactData();





    }

    private void initView(){

        mShowContactBtn = (Button) findViewById(R.id.show_btn);



        mShowContactBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(ManageEditContactActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_show_contact);

        mShowContactLV = (ListView) dialog.findViewById(R.id.show_contact_lv);
        mDialogCloseBtn= (Button) dialog.findViewById(R.id.dialog_close_btn);
        mDialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ViewMultipleContactListAdapter adapter =new ViewMultipleContactListAdapter(this, contactlist);
        mShowContactLV.setAdapter(adapter);

        dialog.show();
    }

    private void getContactData(){
        contactlist = new ArrayList<ContactPersonModel>();

        ContactPersonModel contactPersonModel = new ContactPersonModel();

        contactPersonModel.setName("Ravi V");
        contactPersonModel.setEmailId("ravi@gmail.com");
        contactPersonModel.setPhoneNumber("9292929292");
        contactPersonModel.setRole("CEO");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Naveen G");
        contactPersonModel.setEmailId("Naveen@gmail.com");
        contactPersonModel.setPhoneNumber("9696969696");
        contactPersonModel.setRole("Area Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Rahul");
        contactPersonModel.setEmailId("rahul@gmail.com");
        contactPersonModel.setPhoneNumber("9898998989");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Viraj");
        contactPersonModel.setEmailId("viraj@yahoo.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Sales Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Vishal");
        contactPersonModel.setEmailId("vishal@gmail.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);



    }
}
