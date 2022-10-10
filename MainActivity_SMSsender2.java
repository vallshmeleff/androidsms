package com.example.smssender2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//===================================================
//
// Works. Sends SMS immediately. Doesn't require you to select an application to send
// We use the project to create an SMS messenger with RSA encryption
// Valery Shmelev. Programmer https://www.linkedin.com/in/valery-shmelev-479206227/
// Freelance, outsourcing
//
//===================================================
public class MainActivity extends AppCompatActivity {
    EditText phonenumber;
    EditText smstext;
    Button sendbutton;
    final static String LOG_TAG = "Oflameron";
    private final static int SEND_SMS_PERMISSION_REQ=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumber=findViewById(R.id.editText);
        smstext=findViewById(R.id.editText2);
        sendbutton=findViewById(R.id.button);
        sendbutton.setEnabled(false);

        // RestorePublicKey(); // Get the public key for RSA encryption to send

        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            sendbutton.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=phonenumber.getText().toString();
                String s2=smstext.getText().toString();
                // String s2=PublicKey; // Public RSA key to send
                if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2))
                {

                    if(checkPermission(Manifest.permission.SEND_SMS))
                    {
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(s1,null,s2,null,null);
                    }
                    else {
                        Log.d(LOG_TAG, "== == Permission Denied == ==");
                        //Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Log.d(LOG_TAG, "== == Permission Denied == ==");
                    //Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });


    } //onCreate



    private boolean checkPermission(String sendSms) {

        int checkpermission= ContextCompat.checkSelfPermission(this,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQ:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendbutton.setEnabled(true);
                }
                break;
        }
    }





}