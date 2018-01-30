package com.scottyab.android.restartcounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import de.greenrobot.event.EventBus;

public class OnBootReciver extends BroadcastReceiver {
    public OnBootReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int count = PreferenceManager.getDefaultSharedPreferences(context).getInt(MainActivity.PREF_NO_OF_RESTARTS, 0);
        int newCount = count+1;

        MainActivity.updateCount(context, newCount, System.currentTimeMillis());

        Log.d("onboot", "number of restarts incremented to " + newCount);

    }


}
