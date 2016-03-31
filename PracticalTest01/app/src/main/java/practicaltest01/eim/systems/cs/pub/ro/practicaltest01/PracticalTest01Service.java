package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Alex on 3/31/2016.
 */
public class PracticalTest01Service extends Service {

    private ProcessingThread processingThread;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        float valDiv, valSqrt;
        valDiv = intent.getFloatExtra(Constants.DIV_TAG, 0);
        valSqrt = intent.getFloatExtra(Constants.SQRT_TAG, 0);
        processingThread = new ProcessingThread(this.getApplicationContext(), valDiv, valSqrt);
        processingThread.start();
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
