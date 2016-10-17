package com.example.zer.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;

import static com.example.zer.myapplication.MainActivity.WIFI;

public class WiFiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wm = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

        Intent i = new Intent();
        i.setAction("com.example.zer.myapplication.WIFI");
        ReceiversStates.getInstance().addValue(WIFI, wm.isWifiEnabled());
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(i);
    }
}
