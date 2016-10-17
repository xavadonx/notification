package com.example.zer.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import static com.example.zer.myapplication.MainActivity.BLUETOOTH;

public class BluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Intent i = new Intent();
        i.setAction("com.example.zer.myapplication.BLUETOOTH");
        ReceiversStates.getInstance().addValue(BLUETOOTH, bluetoothAdapter.isEnabled());
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(i);
    }
}
