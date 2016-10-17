package com.example.zer.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Map;

import static android.R.drawable.ic_menu_view;
import static android.R.drawable.ic_search_category_default;

public class MainActivity extends AppCompatActivity {

    public static final int ID = 10;
    public static final int NOTIFY_ID = 10;

    public static final String GSM = "gsm";
    public static final String WIFI = "wifi";
    public static final String BLUETOOTH = "bluetooth";

    private TextView wifiText;
    private TextView bluetoothText;
    private TextView gsmText;

    MyLocalReceiver myLocalReceiver = new MyLocalReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiText = (TextView) findViewById(R.id.wifi_text);
        bluetoothText = (TextView) findViewById(R.id.bluetooth_text);
        gsmText = (TextView) findViewById(R.id.gsm_text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalReceiver,
                new IntentFilter("com.example.zer.myapplication.WIFI"));

        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalReceiver,
                new IntentFilter("com.example.zer.myapplication.BLUETOOTH"));

        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalReceiver,
                new IntentFilter("com.example.zer.myapplication.GSM"));
    }

    public class MyLocalReceiver extends BroadcastReceiver {

        static final int ONE_MODULE_ON = 1;
        static final int TWO_MODULES_ON = 2;
        static final int THREE_MODULES_ON = 3;

        NotificationManager nm;

        @Override
        public void onReceive(Context context, Intent intent) {

            int sum = 0;

            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            for (Map.Entry entry : ReceiversStates.getInstance().getMap().entrySet()) {
                String key = entry.getKey().toString();
                boolean value = (boolean) entry.getValue();

                sum += value ? 1 : 0;

                switch (key) {
                    case WIFI:
                        wifiText.setText(String.format("%s %s", WIFI, value ? "On" : "Off"));
                        break;
                    case BLUETOOTH:
                        bluetoothText.setText(String.format("%s %s", BLUETOOTH, value ? "On" : "Off"));
                        break;
                    case GSM:
                        gsmText.setText(String.format("%s %s", GSM, value ? "On" : "Off"));
                        break;
                }
            }

            switch (sum) {
                case ONE_MODULE_ON:
                    greateNotification(context, "Большой брат присматривает за тобой", ONE_MODULE_ON);
                    break;
                case TWO_MODULES_ON:
                    greateNotification(context, "Большой брат подглядывает за тобой", TWO_MODULES_ON);
                    break;
                case THREE_MODULES_ON:
                    greateNotification(context, "Большой брат следит за тобой", THREE_MODULES_ON);
                    break;
                case 0:
                    nm.cancel(NOTIFY_ID);
                    break;
            }
        }

        public void greateNotification(Context context, String text, int effect) {
            Intent notificationIntent = new Intent();
            PendingIntent contentIntent = PendingIntent.getActivity(context,
                    ID, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(false)
                    .setContentTitle("Title")
                    .setContentText(text);

            switch (effect) {
                case ONE_MODULE_ON:
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), ic_menu_view));
                    break;
                case TWO_MODULES_ON:
                    builder.setVibrate(new long[]{1000, 1000});
                    break;
                case THREE_MODULES_ON:
                    builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), ic_search_category_default));
                    break;
            }

            Notification n = builder.build();
            nm.notify(NOTIFY_ID, n);
        }
    }
}
