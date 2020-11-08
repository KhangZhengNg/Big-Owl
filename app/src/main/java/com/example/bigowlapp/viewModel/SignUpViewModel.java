package com.example.bigowlapp.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.bigowlapp.model.Group;
import com.example.bigowlapp.repository.AuthRepository;
import com.example.bigowlapp.repository.GroupRepository;
import com.google.android.gms.tasks.Task;

public class SignUpViewModel extends ViewModel {

    private AuthRepository authRepository;
    private GroupRepository groupRepository;

    // TODO: Dependency Injection
    public SignUpViewModel() {
        authRepository = new AuthRepository();
        groupRepository = new GroupRepository();
    }

    // TODO: Create a default group upon user creation
    public Task<Boolean> createUser(String email, String password, String phoneNumber, String firstName, String lastName) {
        return authRepository.signUpUser(email, password, phoneNumber, firstName, lastName)
                .addOnSuccessListener(isSuccess -> {
                    Group group = new Group();
                    group.setMonitoringUserId(authRepository.getCurrentUser().getUid());
                    group.setName(firstName + "'s group " + "#" + randomStringGenerator());
                    groupRepository.addDocument(group);
                });

    }

    public String randomStringGenerator() {
        StringBuilder randomStr = new StringBuilder();
        int max = 9;
        int min = 0;
        for (int i = 0; i < 4; i++) {
            randomStr.append((int) ((Math.random() * (max + 1)) + min));
        }
        return randomStr.toString();
    }

}
