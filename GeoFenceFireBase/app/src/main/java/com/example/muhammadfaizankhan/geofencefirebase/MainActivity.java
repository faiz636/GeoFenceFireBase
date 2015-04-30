package com.example.muhammadfaizankhan.geofencefirebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends Activity {


    //user logged-in
    String mUser = "shezi1";

    //user-groups
    ArrayList<String> mUserGroups=new ArrayList<String>();

    // refference address to location defined
    String mGroupLocationDefined = "https://test-employeeconnect.firebaseio.com/group-locations-defined";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        //firebase refferences
        String groups = "https://test-employeeconnect.firebaseio.com/groups";
        String specificGroup = "https://test-employeeconnect.firebaseio.com/group-locations-defined/groupx";
        String userGroupMembership = "https://test-employeeconnect.firebaseio.com/user-group-memberships";
        final String locationTest = "https://test-employeeconnect.firebaseio.com/group-locations-defined-testing";
        String userGroup = userGroupMembership +"/"+mUser;

        //store location objects
        final List<GroupLocation> locations = new ArrayList<GroupLocation>(100);


        Firebase userGroupRef = new Firebase(userGroup);
        userGroupRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String memberOfGroup = dataSnapshot.getKey();
                mUserGroups.add(memberOfGroup);
                String groupLocation = mGroupLocationDefined + "/" + memberOfGroup;
                Log.i("userGroupRef",memberOfGroup);

                Firebase groupLocationRef = new Firebase(groupLocation);

                groupLocationRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> locationMap = (HashMap<String, Object>) dataSnapshot.getValue();
                        Log.i("groupLocationRef",locationMap.toString());
                        locations.add(new GroupLocation(locationMap));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void startAddFenceActivity(View view) {
        //todo:send user groups
        startActivity(new Intent(this, AddFenceActivity.class).putExtra(AddFenceActivity.USER_KEY,mUser)
                .putExtra(AddFenceActivity.USER_GROUP_LOCATION_DEFINED_KEY,mGroupLocationDefined));
    }
    public void startMarkAttendenceActivity(View view) {
//        Bundle bundle = new Bundle();
//        bundle.putString(AddFenceActivity.USER_KEY,mUser);
        //todo:send user groups
        startActivity(new Intent(this, MarkAttendanceActivity.class).putExtra(MarkAttendanceActivity.USER_KEY,mUser));
    }
}
