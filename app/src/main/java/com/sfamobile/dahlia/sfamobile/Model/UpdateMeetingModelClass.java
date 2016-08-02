package com.sfamobile.dahlia.sfamobile.Model;

/**
 * Created by Admin on 02-08-2016.
 */
public class UpdateMeetingModelClass {
    private String MeetingTitle ="";
    private String ClientName = "";
    private String MeetingInfo = "";
    private String Location = "";
    private String Date ="";
    private String Time ="";
    private String SetRemainder ="";

    public String getMeetingTitle() {
        return MeetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        MeetingTitle = meetingTitle;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getMeetingInfo() {
        return MeetingInfo;
    }

    public void setMeetingInfo(String meetingInfo) {
        MeetingInfo = meetingInfo;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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

    public String getSetRemainder() {
        return SetRemainder;
    }

    public void setSetRemainder(String setRemainder) {
        SetRemainder = setRemainder;
    }
}
