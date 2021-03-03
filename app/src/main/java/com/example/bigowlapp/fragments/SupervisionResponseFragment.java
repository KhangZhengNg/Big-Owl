package com.example.bigowlapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.bigowlapp.R;
import com.example.bigowlapp.model.Group;
import com.example.bigowlapp.model.SupervisionRequest;
import com.example.bigowlapp.model.User;
import com.example.bigowlapp.repository.RepositoryFacade;

public class SupervisionResponseFragment extends Fragment {

    private Button acceptBtn;
    private Button rejectBtn;
    private TextView groupName;
    private TextView groupSupervisor;
    private RepositoryFacade repositoryFacade;
    private LiveData<Group> groupLiveData;
    private LiveData<User> userLiveData;
    SupervisionRequest supervisionRequest;

    public SupervisionResponseFragment() {

    }

    public SupervisionResponseFragment(SupervisionRequest sr) {
        this.supervisionRequest = sr;
    }

    public static SupervisionResponseFragment newInstance(SupervisionRequest sr) {
        SupervisionResponseFragment fragment = new SupervisionResponseFragment(sr);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_supervision_response, container, false);

        acceptBtn = view.findViewById(R.id.button_accept);
        rejectBtn = view.findViewById(R.id.button_reject);
        groupName = view.findViewById(R.id.text_view_group_name);
        groupSupervisor = view.findViewById(R.id.text_view_group_supervisor_name);

        repositoryFacade = RepositoryFacade.getInstance();

        userLiveData = repositoryFacade.getUserRepository().getDocumentByUid(supervisionRequest.getSenderUid(), User.class);

        userLiveData.observe(getActivity(), supervisor -> {
            groupSupervisor.setText(supervisor.getFullName());

            groupLiveData = repositoryFacade.getGroupRepository().getDocumentByAttribute("supervisorId", supervisor.getUid(), Group.class);

            groupLiveData.observe(getActivity(), group -> {
                groupName.setText(group.getName());

                acceptBtn.setOnClickListener(view1 -> {
                    group.getMemberIdList().add(supervisionRequest.getReceiverUid());

                    repositoryFacade.getUserRepository().getDocumentByUid(supervisionRequest.getReceiverUid(), User.class).observe(getActivity(), receiver -> {
                        receiver.getMemberGroupIdList().add(group.getUid());

                        repositoryFacade.getGroupRepository().updateDocument(group.getUid(), group);
                        repositoryFacade.getUserRepository().updateDocument(receiver.getUid(), receiver);

                        repositoryFacade.getNotificationRepository().removeDocument(supervisionRequest.getUid());

                        getActivity().onBackPressed();
                    });
                });

                rejectBtn.setOnClickListener(view2 -> {
                    repositoryFacade.getNotificationRepository().removeDocument(supervisionRequest.getUid());

                    getActivity().onBackPressed();
                });
            });
        });

        return view;
    }
}