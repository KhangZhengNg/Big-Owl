package com.example.bigowlapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Notification extends Model {

    public enum Type {
        NONE(Notification.class),
        SUPERVISION_REQUEST(SupervisionRequest.class),
        SCHEDULE_REQUEST(ScheduleRequest.class),
        AUTH_BY_PHONE_NUMBER_FAILURE(AuthByPhoneNumberFailure.class);

        public final Class<? extends Notification> typeClass;

        Type(Class<? extends Notification> typeClass) {
            this.typeClass = typeClass;
        }
    }

    private Type type;
    private Timestamp creationTime;
    private boolean used = false;

    public Notification() {
        super();
        this.type = Type.NONE;
    }

    public Notification(Type type) {
        super();
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    // This private setter is necessary for Firebase data to model mapping
    private void setType(Type type) {
        this.type = type;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
