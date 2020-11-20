package com.example.bigowlapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bigowlapp.R;

public class SendSmsInvitationActivity extends AppCompatActivity {

    private EditText number, message;
    private Button send;
    private String smsNumber, smsMessage, noteText;
    private TextView noteTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms_invitation);

        noteTv = findViewById(R.id.note);

        String contactDetails = getIntent().getStringExtra("contactDetails");
        String contactNumber = getIntent().getStringExtra("contactNumber");

        noteText = "Contact: " + contactDetails + " is not yet registered to the application. Send her/him this invitation text sms";

        noteTv.setText(noteText);

        number = findViewById(R.id.number);
        number.setText(contactNumber);

        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        initialize();
    }

    protected void initialize() {

        try {
            send.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                            beginSendingProcess();
                        } else {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                        }
                    } else beginSendingProcess();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendInvitation() {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(smsNumber, null, smsMessage, null, null);
            Toast.makeText(this, "Invitation sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error, Message not sent", Toast.LENGTH_SHORT).show();
        }
    }

    private void beginSendingProcess() {
        smsNumber = number.getText().toString().trim();
        smsMessage = message.getText().toString().trim();

        if (smsMessage.isEmpty() && smsNumber.isEmpty()) {
            Toast.makeText(SendSmsInvitationActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
        } else if (smsNumber.isEmpty()) {
            number.setError("Please enter a phone number");
            number.requestFocus();
        } else if (smsMessage.isEmpty()) {
            message.setError("Please enter a message");
            message.requestFocus();
        } else {
            sendInvitation();
            Intent intent = new Intent(SendSmsInvitationActivity.this, InvitationConfirmationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}