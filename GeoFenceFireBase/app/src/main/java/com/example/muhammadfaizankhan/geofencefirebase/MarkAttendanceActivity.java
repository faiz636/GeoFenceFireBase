package com.example.muhammadfaizankhan.geofencefirebase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MarkAttendanceActivity extends ActionBarActivity {

    static String USER_KEY="user";//key to receive user name form intent

    //MarkAttendanceFireBase class marks attendence.
    MarkAttendanceFireBase mMarkAttendenceFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        String user= getIntent().getStringExtra(AddFenceActivity.USER_KEY);

        //todo:get group from intent and poppulate droup down list

        //making object and setting certain values
        mMarkAttendenceFirebase = new MarkAttendanceFireBase(user,
                MarkAttendanceFireBase.SOURCE_DEVICE_ANDROID,MarkAttendanceFireBase.SOURCE_TYPE_GEOFENCING);

    }

    //onclick listener to mark attendence for entrance
    public void markAttendenceIn(View view){

        String ofGroup, title, message;
        int lon,lat;

        try {
            ofGroup = ((EditText) findViewById(R.id.ETgroupName)).getText().toString();
            if (ofGroup.startsWith(" ") || ofGroup.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Group Field");

            title = ((EditText) findViewById(R.id.ETtitle)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            message = ((EditText) findViewById(R.id.ETmessage)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            lon = Integer.parseInt(((EditText) findViewById(R.id.lan)).getText().toString());
            if(lon<1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");

            lat = Integer.parseInt(((EditText) findViewById(R.id.lan)).getText().toString());
            if(lat<1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");

            mMarkAttendenceFirebase.markAttendance(ofGroup,title,message,MarkAttendanceFireBase.TYPE_IN,24,60);

        } catch (IllegalArgumentException exobj) {
            Toast.makeText(this, exobj.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //onclick listener to mark attendance for exit
    public void markAttendenceOut(View view){

        String ofGroup, title, message;
        int lon,lat;

        try {
            ofGroup = ((EditText) findViewById(R.id.ETgroupName)).getText().toString();
            if (ofGroup.startsWith(" ") || ofGroup.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Group Field");

            title = ((EditText) findViewById(R.id.ETtitle)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            message = ((EditText) findViewById(R.id.ETmessage)).getText().toString();
            if (title.startsWith(" ") || title.length() == 0)
                throw new IllegalArgumentException("Wrong Data in Title Field");

            lon = Integer.parseInt(((EditText) findViewById(R.id.lan)).getText().toString());
            if(lon<1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");

            lat = Integer.parseInt(((EditText) findViewById(R.id.lan)).getText().toString());
            if(lat<1)
                throw new IllegalArgumentException("Wrong Data in Radius Field");

            mMarkAttendenceFirebase.markAttendance(ofGroup,title,message,MarkAttendanceFireBase.TYPE_OUT,24,60);

        } catch (IllegalArgumentException exobj) {
            Toast.makeText(this, exobj.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
