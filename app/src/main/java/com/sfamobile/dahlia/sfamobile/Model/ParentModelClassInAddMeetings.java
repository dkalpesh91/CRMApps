package com.sfamobile.dahlia.sfamobile.Model;

import java.util.ArrayList;

/**
 * Created by Admin on 02-08-2016.
 */
public class ParentModelClassInAddMeetings {

    private String MeetingTitle ="";
    private String Date ="";
    private String Time ="";


    private ArrayList<ChildModelClassInAddMeetings> children;

    public String getMeetingTitle() {
        return MeetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        MeetingTitle = meetingTitle;
    }



    public ArrayList<ChildModelClassInAddMeetings> getChildren()
    {
        return children;
    }

    public void setChildren(ArrayList<ChildModelClassInAddMeetings> children)
    {
        this.children = children;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

