package com.example.sendsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private EditText txtMobile;
    private EditText txtMessage;
    private Button btnSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMobile = findViewById(R.id.mblTxt);
        txtMessage = findViewById(R.id.msgTxt);
        btnSms = findViewById(R.id.btnSend);
        btnSms.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)) {
            btnSms.setEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = txtMessage.getText().toString();
                String phone = txtMobile.getText().toString();

                if(!TextUtils.isEmpty(msg) && !TextUtils.isEmpty(phone)) {
                    if(checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, msg, null, null);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermission(String sendSms) {
        int checkPermission = ContextCompat.checkSelfPermission(this, sendSms);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }


    public void onRequestPermissionResult(int requestCode, String [] permission, int [] grantResults) {
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                btnSms.setEnabled(true);
        }
    }
}
