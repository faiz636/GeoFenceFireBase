package com.example.muhammadfaizankhan.geofencefirebase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.GregorianCalendar;


public class AddFenceActivity extends ActionBarActivity {

    //key to get data from intent
    static String USER_KEY = "user",
            USER_GROUP_LOCATION_DEFINED_KEY="GroupLocationDefined";
    String mUser,mGroupLocationDefinedAddress;
    int mType=1;
    double mLongitude=20.0000,mLatitude=20.0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fence);
        mUser= getIntent().getStringExtra(AddFenceActivity.USER_KEY);
        mGroupLocationDefinedAddress= getIntent().getStringExtra(AddFenceActivity.USER_GROUP_LOCATION_DEFINED_KEY);
    }


    public void addFence(View view) {

        String ofGroup;
        String definedBy;
        String title;
        int radius;

        try {
            ofGroup = ((EditText) findViewById(R.id.ETgroupName)).getText().toString();
            if (ofGroup.startsWith(" ") || ofGroup.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Group Field");

            title = ((EditText) findViewById(R.id.ETtitle)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            radius = Integer.parseInt(((EditText) findViewById(R.id.ETradius)).getText().toString());
            if(radius<1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");

        } catch (IllegalArgumentException exobj) {
            Toast.makeText(this, exobj.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


    Firebase groupLocationDefinedRef = new Firebase(mGroupLocationDefinedAddress);
        groupLocationDefinedRef.child(ofGroup).push().setValue(new GroupLocation(mUser,ofGroup,title,mType,mLatitude,mLongitude,radius, new GregorianCalendar()).getMapObject());

    }

}
