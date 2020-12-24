package com.example.bigowlapp.viewModel;

import com.example.bigowlapp.model.LiveDataWithStatus;
import com.example.bigowlapp.model.User;

public class HomePageViewModel extends BaseViewModel {

    private LiveDataWithStatus<User> user;

    public HomePageViewModel() {
    }

    public LiveDataWithStatus<User> getCurrentUserData() {
        if (user == null) {
            user = new LiveDataWithStatus<>();
            loadUserCurrentProfile();
        }
        return user;
    }

    private void loadUserCurrentProfile() {
        user = repositoryFacade.getUserRepository()
                .getDocumentByUid(getCurrentUserUid(), User.class);
    }
}
