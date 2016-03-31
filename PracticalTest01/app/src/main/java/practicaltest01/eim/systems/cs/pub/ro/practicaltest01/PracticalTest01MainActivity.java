package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private Button navigateButton;
    private Button pressMeButton;
    private Button pressMeTooButton;
    private EditText editText1;
    private EditText editText2;

    private StartedServiceBroadcastReceiver startedServiceBroadcastReceiver;
    private IntentFilter startedServiceIntentFilter;

    private boolean isServiceStarted;

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int val;
            String value;
            Button button = (Button) v;
            switch (v.getId()) {
                case R.id.button_press_me:
                    value = editText1.getText().toString();
                    val = Integer.valueOf(value);
                    val++;
                    editText1.setText(String.valueOf(val));
                    checkThreshold();
                    break;
                case R.id.button_press_me_too:
                    value = editText2.getText().toString();
                    val = Integer.valueOf(value);
                    val++;
                    editText2.setText(String.valueOf(val));
                    checkThreshold();
                    break;
                case R.id.button_navigate:
                    Intent intent = new Intent(PracticalTest01MainActivity.this,
                            PracticalTest01SecondaryActivity.class);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQ_CODE);
                    break;
            }
        }
    }

    private void checkThreshold() {
        float val1, val2;
        val1 = Integer.valueOf(editText1.getText().toString());
        val2 = Integer.valueOf(editText2.getText().toString());
        if ((val1 + val2) > Constants.THRESHOLD && !isServiceStarted) {
            Intent intent = new Intent(this, PracticalTest01Service.class);
            intent.putExtra(Constants.SQRT_TAG, (float)(Math.sqrt(val1 * val2)));
            intent.putExtra(Constants.DIV_TAG, (val1 + val2)/2);
            Toast.makeText(this, "Service: " + Math.sqrt(val1 * val2) + ", " + (val1 + val2)/2, Toast.LENGTH_LONG).show();
            startService(intent);
            isServiceStarted = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        navigateButton = (Button) findViewById(R.id.button_navigate);
        pressMeButton = (Button) findViewById(R.id.button_press_me);
        pressMeTooButton = (Button) findViewById(R.id.button_press_me_too);

        ButtonClickListener buttonClickListener = new ButtonClickListener();

        pressMeButton.setOnClickListener(buttonClickListener);
        pressMeTooButton.setOnClickListener(buttonClickListener);
        navigateButton.setOnClickListener(buttonClickListener);

        startedServiceBroadcastReceiver = new StartedServiceBroadcastReceiver();
        startedServiceIntentFilter = new IntentFilter();
        startedServiceIntentFilter.addAction(Constants.ACTION1);
        startedServiceIntentFilter.addAction(Constants.ACTION2);
        startedServiceIntentFilter.addAction(Constants.ACTION3);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(startedServiceBroadcastReceiver, startedServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(startedServiceBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.EDIT_TEXT1_TAG, editText1.getText().toString());
        outState.putString(Constants.EDIT_TEXT2_TAG, editText2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.EDIT_TEXT1_TAG)) {
            editText1.setText(savedInstanceState.getString(Constants.EDIT_TEXT1_TAG));
        }
        if (savedInstanceState.containsKey(Constants.EDIT_TEXT2_TAG)) {
            editText2.setText(savedInstanceState.getString(Constants.EDIT_TEXT2_TAG));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String message = "Result Code : ";
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQ_CODE)
            Toast.makeText(this, message + resultCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
    }
}
