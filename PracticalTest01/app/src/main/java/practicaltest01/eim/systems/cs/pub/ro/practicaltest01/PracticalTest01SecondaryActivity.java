package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Alex on 3/31/2016.
 */
public class PracticalTest01SecondaryActivity extends Activity {

    private Intent intentFromParent;
    private Intent intentToParent;

    private Button buttonOk;
    private Button buttonCancel;

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            intentToParent = new Intent();
            int resultCode = RESULT_OK;
            switch (v.getId()) {
                case R.id.button_ok :
                    resultCode = RESULT_OK;
                    break;
                case R.id.button_cancel :
                    resultCode = RESULT_CANCELED;
                    break;
            }
            setResult(resultCode, intentToParent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        intentFromParent = getIntent();
        buttonOk = (Button) findViewById(R.id.button_ok);
        buttonCancel = (Button) findViewById(R.id.button_cancel);

        ButtonClickListener buttonClickListener = new ButtonClickListener();

        buttonOk.setOnClickListener(buttonClickListener);
        buttonCancel.setOnClickListener(buttonClickListener);
    }
}
