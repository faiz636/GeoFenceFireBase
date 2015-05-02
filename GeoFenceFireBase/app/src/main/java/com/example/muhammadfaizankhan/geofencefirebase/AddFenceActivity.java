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
    static String USER_KEY = "user",
            USER_GROUP_LOCATION_DEFINED_KEY = "GroupLocationDefined",
            USER_GROUP = "usergroup";
    String mUser, mGroupLocationDefinedAddress;
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
        mGroupLocationDefinedAddress = getIntent().getStringExtra(AddFenceActivity.USER_GROUP_LOCATION_DEFINED_KEY);

        //spinner reference
        Spinner spinner = (Spinner) findViewById(R.id.SPGroupName);

        //setting on selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, groups);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

//    bt.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String abc;
//            abc = (String) tv.getText().toString();
//            categories.add(abc);
//        }
//    });
//
//    // Spinner click listener
//    bt1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            String item = (String) spinner.getSelectedItem();
//            //.getItemAtPosition(position).toString();
//            Toast.makeText(MainActivity.this, "Selected: " + item, Toast.LENGTH_LONG).show();
//        }
//    });



    //onclick listener of button. this method read data form fields and add fence to firebase
    public void addFence(View view) {

        String ofGroup;
        String definedBy;
        String title;
        int radius;

        //reading and checking data correctness if incorrrect data is entered exception is created
        try {

            //group name
            ofGroup = ((Spinner)findViewById(R.id.SPGroupName)).getSelectedItem().toString();
            Toast.makeText(AddFenceActivity.this, "Selected: " + ofGroup, Toast.LENGTH_SHORT).show();

//            ofGroup = ((EditText) findViewById(R.id.ETgroupName)).getText().toString();
//            if (ofGroup.startsWith(" ") || ofGroup.length() == 0)
//                throw new IllegalArgumentException("Wrong Data in Group Field");

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
        Firebase groupLocationDefinedRef = new Firebase(mGroupLocationDefinedAddress);
        //setting location object at firebase
        groupLocationDefinedRef.child(ofGroup).push().setValue(new GroupLocation(mUser, ofGroup, title, mType, mLatitude, mLongitude, radius, new GregorianCalendar()).getMapObject());

    }

}
