package com.sfamobile.dahlia.sfamobile.Model;

/**
 * Created by Admin on 02-08-2016.
 */
public class ChildModelClassInAddMeetings {

    private String ClientName = "";
    private String MeetingInfo = "";
    private String Location = "";
    private String SetRemainder ="";


    public ChildModelClassInAddMeetings(){

        this.MeetingInfo = MeetingInfo;
        this.ClientName = ClientName;
        this.Location = Location;

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



    public String getSetRemainder() {
        return SetRemainder;
    }

    public void setSetRemainder(String setRemainder) {
        SetRemainder = setRemainder;
    }
}
