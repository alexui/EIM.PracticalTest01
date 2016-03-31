package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Context;
import android.content.Intent;

import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by Alex on 3/31/2016.
 */
public class ProcessingThread extends Thread{

    private Context context;

    private float divVal;
    private float squareVal;
    private boolean activeThread;

    private Random random;

    private String actions[] = {Constants.ACTION1, Constants.ACTION2, Constants.ACTION3};

    public ProcessingThread(Context context, float divVal, float squareVal) {
        this.context = context;
        this.divVal = divVal;
        this.squareVal = squareVal;
        this.activeThread = true;
        random = new Random();
//        Log.d(Constants.LOGCAT_FILTER_TAG, "data: " + squareVal + " " + divVal);
    }


    @Override
    public void run() {
        while (activeThread) {

            int randVal = random.nextInt(3);
            String action = actions[randVal];
            sendMessage(action);
            sleep();
        }
    }

    public void stopThread() {
        this.activeThread = false;
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String action) {
        Intent intent = new Intent();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String data = timestamp + "--" + this.divVal + "--" + this.squareVal;

        intent.setAction(action);
        intent.putExtra(Constants.DATA, data);

        context.sendBroadcast(intent);
    }
}
