package com.example.muhammadfaizankhan.geofencefirebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    final static String USER_KEY = "user";

    //user logged-in
    String mUser = "shezi1";

    //user-groups
    List<String> mUserGroups;//contain names of user groups
    List<GroupLocation> mGroupLocations;//contain locations of user groups

    //firebase url of main application
    String mFirebaseUrl = "https://test-employeeconnect.firebaseio.com";

    //firebase url to location defined
    String mGroupLocationDefinedAddressUrl = mFirebaseUrl + "/group-locations-defined";

    //firebase url to user group relationship metadata
    String mUserGroupMembershipAddressUrl = mFirebaseUrl + "/user-group-memberships";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting firebase context
        Firebase.setAndroidContext(this);

        //making firebase refferenc to user-group-membership
        String userGroupAddress = mUserGroupMembershipAddressUrl +"/"+mUser;

        //contain names of user groups
        mUserGroups=new ArrayList<String>();

        //store location objects with max capacity 100
        mGroupLocations = new ArrayList<GroupLocation>(100);

        //making refference to firebase user-group-membership/current-user
        Firebase userGroupRef = new Firebase(userGroupAddress);

        //adding child listener to request groups names of user
        userGroupRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //getting group name of user
                String groupName = dataSnapshot.getKey();

                //adding group name to user groups
                mUserGroups.add(groupName);

                //refferencing to group location at firebase of group
                String groupLocation = mGroupLocationDefinedAddressUrl + "/" + groupName;
//                Log.i("userGroupRef",groupName);

                //making firebase refference object to add listener
                Firebase groupLocationRef = new Firebase(groupLocation);

                //adding child listener to get location of group
                groupLocationRef.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        //getting location data form data recived
                        Map<String, Object> locationMap = (HashMap<String, Object>) dataSnapshot.getValue();

//                        Log.i("groupLocationRef",locationMap.toString());

                        //adding this location to list of locations
                        mGroupLocations.add(new GroupLocation(locationMap));
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

    //onclick listener of button to start add new fence activity
    public void startAddFenceActivity(View view) {

        Log.i("startAddFenceActivity",mUserGroups.size()+"");
        startActivity(new Intent(this, AddFenceActivity.class)  //creating intent object to call AddFenceActivity
                //setting extra values to intent
                .putExtra(AddFenceActivity.USER_KEY, mUser)     //setting user name
                //setting mGroupLocationDefinedAddressUrl which is url at firebase
                .putExtra(AddFenceActivity.USER_GROUP_LOCATION_DEFINED_URL_KEY, mGroupLocationDefinedAddressUrl)
                //setting user group names to which fence will be added
                .putExtra(AddFenceActivity.USER_GROUP, (Serializable) mUserGroups));
    }

    //onclick listener of button to start mark attendence activity
    public void startMarkAttendenceActivity(View view) {
        //todo:send user groups
        startActivity(new Intent(this, MarkAttendanceActivity.class).putExtra(MarkAttendanceActivity.USER_KEY, mUser));
    }
}
