package ru.exlmoto.synergycalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by exl on 9/12/16.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, SynergyService.class);
        context.startService(startServiceIntent);
    }
}
