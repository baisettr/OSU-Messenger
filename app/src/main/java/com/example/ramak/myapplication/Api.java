package com.example.ramak.myapplication;

/**
 * Created by ramak on 10/17/2017.
 */

public class Api {

    //private static final String ROOT_URL = "http://10.249.88.58:3000/osu/";
    //private static final String ROOT_URL = "http://10.0.0.226:5000/";
    private static final String ROOT_URL = "https://osumsg.herokuapp.com/";

    public static final String URL_REGISTER_USER = ROOT_URL + "regUser";
    public static final String URL_VALIDATE_USER = ROOT_URL + "valUser";
    public static final String URL_LISTLOCATIONS_USER = ROOT_URL + "listLocations";
    public static final String URL_GETUSERDETAILS_USER = ROOT_URL + "getUserDetails";
    public static final String URL_UPDATEUSERLOCATION_USER = ROOT_URL + "updateUserLocation";
    public static final String URL_REQUESTUSER_USER = ROOT_URL + "requestUser";
    public static final String URL_ACCEPTUSER_USER = ROOT_URL + "acceptUser";
    public static final String URL_UPDATEUSER_USER = ROOT_URL + "updateUser";
    public static final String URL_GETMAILBOX_USER = ROOT_URL + "getMailBox";
    public static final String URL_GETMESSAGES_USER = ROOT_URL + "getMessages";
    public static final String URL_INSERTMESSAGE_USER = ROOT_URL + "insertMessage";

}
