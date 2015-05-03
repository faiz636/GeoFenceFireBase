package com.example.muhammadfaizankhan.geofencefirebase;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class AddFenceActivity extends ActionBarActivity {

    //key to get data from intent
    final static String USER_KEY = "user",
            USER_GROUP_LOCATION_DEFINED_URL_KEY = "GroupLocationDefined",
            USER_GROUP = "usergroup";
    String mUser, mGroupLocationDefinedAddressUrl;
    int mType = 1;
    double mLongitude = 20.0000, mLatitude = 20.0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fence);

        //getting user name passed to this activity
        mUser = getIntent().getStringExtra(AddFenceActivity.USER_KEY);

        //list for spinner
        List<String> groups = (ArrayList<String>)getIntent().getSerializableExtra(AddFenceActivity.USER_GROUP);//new ArrayList<String>();

        //getting group-location-defined address passed to this activity
        mGroupLocationDefinedAddressUrl = getIntent().getStringExtra(AddFenceActivity.USER_GROUP_LOCATION_DEFINED_URL_KEY);

        //todo: get longitude and latitude also mType

        //spinner reference
        Spinner spinner = (Spinner) findViewById(R.id.SPGroupName);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, groups);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }



    //onclick listener of button. this method read data form fields and add fence to firebase
    public void addFence(View view) {

        String ofGroup;
        String title;
        int radius;

        //reading and checking data correctness if incorrrect data is entered exception is created
        try {

            //group name
            ofGroup = ((Spinner)findViewById(R.id.SPGroupName)).getSelectedItem().toString();
            Toast.makeText(AddFenceActivity.this, "Selected: " + ofGroup, Toast.LENGTH_SHORT).show();

            title = ((EditText) findViewById(R.id.ETtitle)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            radius = Integer.parseInt(((EditText) findViewById(R.id.ETradius)).getText().toString());
            if (radius < 1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");
        }
        //handling exceptions thrown in try block
        catch (IllegalArgumentException exobj) {
            Toast.makeText(this, exobj.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        //making refference object to firebase group-location-defined to add fence location object
        Firebase groupLocationDefinedRef = new Firebase(mGroupLocationDefinedAddressUrl);
        //setting location object at firebase
        groupLocationDefinedRef.child(ofGroup).push().setValue(
                new GroupLocation(mUser, ofGroup, title, mType, mLatitude, mLongitude, radius,
                        new GregorianCalendar()).getMapObject());

    }

}
