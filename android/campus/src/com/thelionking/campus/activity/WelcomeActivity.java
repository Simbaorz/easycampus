package com.thelionking.campus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;

/**
 * @author the lion king
 * @version 1.0
 */
public class WelcomeActivity extends Activity implements MainApp.ReadyListener{
    public static final String TAG = "WelcomeActivity";

    public static final int SKIP = 0X000;

    private MsgHandler msgHandler =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        msgHandler = new MsgHandler();
        ready();
    }

    @Override
    public void onReadySuccessed(int code) {
        msgHandler.sendEmptyMessage(SKIP);
    }

    @Override
    public void onReadyFailed(int code) {

    }

    private class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SKIP : {
                    skip();
                    break;
                }
            }
        }
    }

    private void skip() {
        Intent intent = new Intent(this, SchoolSelectorActivity.class);
        startActivity(intent);
        finish();
    }

    private void ready() {
        MainApp.getInstance().initData(this);
    }
}
