package com.example.bigowlapp.utils;


import androidx.appcompat.app.AppCompatActivity;

import com.example.bigowlapp.model.Schedule;
import com.example.bigowlapp.model.User;
import com.example.bigowlapp.repository.RepositoryFacade;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class IsSmsSenderACurrentEventSupervisor extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public IsSmsSenderACurrentEventSupervisor() {

    }

    public Task<List<Schedule>> check(String phoneNumber) {
        RepositoryFacade repositoryFacade = RepositoryFacade.getInstance();
        String currentUserUid = repositoryFacade.getAuthRepository().getCurrentUser().getUid();

        Task<QuerySnapshot> gettingUserTask = db.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get();
        Task<User> userTask = gettingUserTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    List<User> listOfUsers = task.getResult().toObjects(User.class);
                    return Tasks.forResult(listOfUsers.get(0));
                } else throw task.getException();
            } else throw task.getException();
        });

        Task<List<Schedule>> schedulesTask = userTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                User supervisorUser = task.getResult();
                Task<QuerySnapshot> gettingSchedulesTask = db.collection("schedules")
                        .whereEqualTo("groupSupervisorUid", supervisorUser.getUid())
                        .whereArrayContains("memberList", currentUserUid)
                        .get();

                Task<List<Schedule>> handleSchedulesTask = gettingSchedulesTask.continueWithTask(task2 -> {
                    if (task2.isSuccessful()) {
                        if (!task2.getResult().isEmpty()) {
                            List<Schedule> listOfSchedules = task2.getResult().toObjects(Schedule.class);
                            return Tasks.forResult(listOfSchedules);
                        } else {
                            throw task2.getException();
                        }
                    } else {
                        throw task2.getException();
                    }
                });

                return handleSchedulesTask;
            } else {
                throw task.getException();
            }

        });
        return schedulesTask;
    }
}