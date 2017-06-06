package com.murach.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ReminderActivity extends Activity implements OnClickListener {

    private Button startServiceButton;
    private Button stopServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        stopServiceButton = (Button) findViewById(R.id.stopServiceButton);

        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        Intent service = new Intent(this, ReminderService.class);

        switch (v.getId()) {
            case R.id.startServiceButton:
                // put code to start service and display toast here
                startService(service);
                Toast start= Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT);
                start.show();

                break;
            case R.id.stopServiceButton:
                // put code to stop service and display toast here
                stopService(service);
                Toast stop = Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT);
                stop.show();
                break;
        }
    }
}