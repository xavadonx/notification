package com.example.zer.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;

import static com.example.zer.myapplication.MainActivity.GSM;

public class GSMReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isEnabled = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

        Intent i = new Intent();
        i.setAction("com.example.zer.myapplication.GSM");
        ReceiversStates.getInstance().addValue(GSM, isEnabled);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(i);
    }
}
