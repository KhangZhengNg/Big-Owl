package com.example.bigowlapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bigowlapp.model.Schedule;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository extends Repository<Schedule> {

    // TODO: Add dependency injection
    public ScheduleRepository() {
        super("schedules");
    }

    public Task<Void> updateScheduleMemberResponse(String scheduleId, String userUId, Schedule.UserResponse currentUserResponse) {
        return collectionReference.document(scheduleId).update("members.".concat(userUId), currentUserResponse);
    }

    public MutableLiveData<List<Schedule>> getListSchedulesFromGroupForUser(String groupID, String userID) {
        MutableLiveData<List<Schedule>> listOfTData = new MutableLiveData<>();
        collectionReference.whereEqualTo("groupUId", groupID)
                .whereArrayContains("membersList", userID)
                .orderBy("startTime", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot tDocs = task.getResult();
                        if (tDocs != null && !tDocs.isEmpty()) {
                            List<Schedule> listOfT = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Schedule t = doc.toObject(Schedule.class);
                                listOfT.add(t);
                            }
                            listOfTData.setValue(listOfT);
                        } else {
                            listOfTData.setValue(null);
                        }
                    } else {
                        Log.e(getClassName(), "Error getting documents: " +
                                task.getException());
                    }
                });
        return listOfTData;
    }


}