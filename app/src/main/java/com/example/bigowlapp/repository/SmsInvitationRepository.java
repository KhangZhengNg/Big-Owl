package com.example.bigowlapp.repository;

import com.example.bigowlapp.model.SmsInvitationRequest;

public class SmsInvitationRepository extends Repository<SmsInvitationRequest> {
    public static final String COLLECTION_NAME = "SmsInvitations";

    public SmsInvitationRepository() {
        super(SmsInvitationRepository.COLLECTION_NAME);
    }
}
