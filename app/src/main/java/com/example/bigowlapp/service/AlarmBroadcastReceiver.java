package com.example.bigowlapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.bigowlapp.model.Schedule;
import com.google.firebase.firestore.GeoPoint;

/**
 * The purpose of this BroadcastReceiver is to execute code after the time activation of an alarm.
 * The alarms are set/defined in
 * {@link com.example.bigowlapp.utils.AlarmBroadcastReceiverManager}
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final String EXTRA_UID = "Uid";
    private final String EXTRA_LATITUDE = "Latitude";
    private final String EXTRA_LONGITUDE = "Longitude";
    private final double DEFAULT_VALUE = 0.0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Schedule activatedSchedule = getSchedule(intent);
        // Run the location/Geofencing code
        // activatedSchedule should only have UID, LONGITUDE, LATITUDE
    }

    private Schedule getSchedule(Intent intent) {
        Schedule schedule = new Schedule();
        schedule.setUid(intent.getStringExtra(EXTRA_UID));
        GeoPoint geoPoint = new GeoPoint(intent.getDoubleExtra(EXTRA_LATITUDE, DEFAULT_VALUE),
                intent.getDoubleExtra(EXTRA_LONGITUDE, DEFAULT_VALUE));
        schedule.setLocation(geoPoint);
        return schedule;
    }

}
