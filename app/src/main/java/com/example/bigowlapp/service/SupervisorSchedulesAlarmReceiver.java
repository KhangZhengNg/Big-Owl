package com.example.bigowlapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.bigowlapp.model.Attendance;
import com.example.bigowlapp.model.Schedule;
import com.example.bigowlapp.model.User;
import com.example.bigowlapp.repository.RepositoryFacade;
import com.example.bigowlapp.utils.SmsSender;
import com.example.bigowlapp.utils.SupervisorSchedulesAlarmManager;

/**
 * The purpose of this BroadcastReceiver is to execute code after the time activation of an alarm.
 * The alarms are set/defined in
 * {@link SupervisorSchedulesAlarmManager}
 */
public class SupervisorSchedulesAlarmReceiver extends BroadcastReceiver {

    RepositoryFacade repositoryFacade = RepositoryFacade.getInstance();
    SmsSender smsSender = new SmsSender();

    @Override
    public void onReceive(Context context, Intent intent) {
        String scheduleId = intent.getStringExtra("scheduleUid");
        repositoryFacade.getScheduleRepository().getDocumentByUid(scheduleId, Schedule.class)
                .observeForever(supervisorSchedule -> {
                    if (supervisorSchedule == null) {
                        return;
                    } else {
                        for (String scheduleMemberUid : supervisorSchedule.getMemberList()) {
                            if ((supervisorSchedule.getUserScheduleResponseMap().get(scheduleMemberUid)
                                    .getAttendance().getScheduleLocated() == Attendance.LocatedStatus.NOT_DETECTED) && supervisorSchedule.getUserScheduleResponseMap().get(scheduleMemberUid)
                                    .getAttendance().isAuthenticated() == false) {
                                repositoryFacade.getUserRepository()
                                        .getDocumentByUid(scheduleMemberUid, User.class)
                                        .observeForever(scheduleMember -> {
                                            String scheduleMemberPhoneNum = scheduleMember.getPhoneNumber();
                                            smsSender.sendSMS(context, scheduleMemberPhoneNum,
                                                    "ATTENTION: BigOwl wasn't able to get your current location for" +
                                                            " your next attendance. Please make sure your internet and " +
                                                            "BigOwl app are on");
                                        });

                            }
                        }
                    }
                });

    }
}