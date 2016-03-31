package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    private Intent startServiceActivityIntent;

    public StartedServiceBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        String data = intent.getStringExtra(Constants.DATA);

        Log.d(Constants.LOGCAT_FILTER_TAG, "Action: " + action + ", data :" + data);

    }

}