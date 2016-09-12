package ru.exlmoto.synergycalls;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SynergyService extends Service {

    public static final String LOG_TAG = "SynergyService";

    private CallReceiver callReceiver = null;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        callReceiver = new CallReceiver();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        // someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    void someTask() {
        // Empty
    }
}
