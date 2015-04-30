package com.example.muhammadfaizankhan.geofencefirebase;

import android.location.Location;
import android.util.Log;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Muhammad Faizan Khan on 24/04/2015.
 */
public class GroupLocation {


    String mDefinedBy,//name of user who define the location
            mTeamURL, //contain group name of location
            mTitle;     //name of location
    int mType;
    GregorianCalendar mTimestamp;
    int mRadius;//radius of fence
    Location mLocation;//location object which contain latitude and longitude

    public GroupLocation(String definedBy, String teamURL, String title, int type,
                         double latitude, double longitude, int radius, GregorianCalendar timestamp) {
        this.mDefinedBy = definedBy;
        this.mTeamURL = teamURL;
        this.mTitle = title;
        this.mType = type;
        this.mTimestamp = timestamp;
        this.mRadius = radius;

        this.mLocation = new Location(mTeamURL +"/"+ mTitle);
        this.mLocation.setLatitude(latitude);
        this.mLocation.setLongitude(longitude);
    }

    //this constructor is passed complete map object obtained form firebase for each location
    public GroupLocation(Map<String,Object> map){

        this.mDefinedBy = (String)map.get("defined-by");
        this.mTeamURL = (String)map.get("team-url");
        this.mTitle = (String)map.get("title");
        this.mType = Integer.parseInt(map.get("type").toString());


        this.mTimestamp = new GregorianCalendar();
                mTimestamp.setTimeInMillis(Long.parseLong(map.get("timestamp").toString()));


        //getting inner node 'location'
        Map<String,Object> location= (Map<String,  Object>) map.get("location");

        this.mRadius= Integer.parseInt(location.get("radius").toString());

        this.mLocation = new Location(mTeamURL +"/"+ mTitle);
        this.mLocation.setLatitude(Double.parseDouble(location.get("lat").toString()));
        this.mLocation.setLongitude(Double.parseDouble(location.get("lon").toString()));

    }

    //this method returns map object according to firebase location structure
    public Map<String,Object> getMapObject(){

        Map<String,Object> map= new HashMap<String,Object>();
        map.put("defined-by", mDefinedBy);
        map.put("team-url", mTeamURL);
        map.put("title", mTitle);
        map.put("type", mType);
        map.put("timestamp", mTimestamp.getTimeInMillis());

        //making inner node
        Map<String,Object> location= new HashMap<String,Object>();
        location.put("radius",mRadius);
        location.put("lat", this.mLocation.getLatitude());
        location.put("lon",this.mLocation.getLongitude());

        map.put("location",location);
        return map;

    }

}
