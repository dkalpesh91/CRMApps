package com.sfamobile.dahlia.sfamobile.Model;

import android.content.Context;

/**
 * Created by Admin on 01-08-2016.
 */
public class Catalog {
    private String nameSaving = "";
    private String rollSaving = "";
    private String contactSaving ;
    private String emailSaving = "";
    private Context context;
    private String listDataHeader ="";

    public Catalog() {
        //  this.context = context;
        this.nameSaving = nameSaving;
        this.rollSaving= rollSaving;
        this.contactSaving = contactSaving;
        this.emailSaving = emailSaving;
    }


    public String getNameSaving() {
        return nameSaving;
    }

    public void setNameSaving(String nameSaving) {
        this.nameSaving = nameSaving;
    }

    public String getRollSaving() {
        return rollSaving;
    }

    public void setRollSaving(String rollSaving) {
        this.rollSaving = rollSaving;
    }

    public String getContactSaving() {
        return contactSaving;
    }

    public void setContactSaving(String contactSaving) {
        this.contactSaving = contactSaving;
    }

    public String getEmailSaving() {
        return emailSaving;
    }

    public void setEmailSaving(String emailSaving) {
        this.emailSaving = emailSaving;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public String getListDataHeader() {
        return listDataHeader;
    }

    public void setListDataHeader(String listDataHeader) {
        this.listDataHeader = listDataHeader;
    }
}
