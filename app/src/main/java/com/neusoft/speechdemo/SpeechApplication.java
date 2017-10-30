package com.neusoft.speechdemo;

import android.app.Application;
import android.util.Log;

import com.iflytek.cloud.SpeechUtility;
import com.neusoft.speechdemo.speech.Speech;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

/**
 * Created by yangming on 17-10-26.
 */
public class SpeechApplication extends Application {

    private static final String TAG = SpeechApplication.class.getSimpleName();

    private static SpeechApplication instance;

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        super.onCreate();
        instance = this;
        Speech.getInstance().subscribeOnSpeechInitListener(new OnSpeechInitListener() {
            @Override
            public void onInitSuccess() {
                Log.d(TAG, "Speech init Completed");
                Speech.getInstance().speak("讯飞语音初始化成功", 0);
                Speech.getInstance().unSubscribeOnSpeechInitListener(this);
            }

            @Override
            public void onInitError(int errorCode) {
                Log.d(TAG, "Speech init Error, ErrorCode = " + errorCode);
                Speech.getInstance().unSubscribeOnSpeechInitListener(this);
            }
        });
        Speech.getInstance().init(this);
    }

    public static SpeechApplication getInstance() {
        return instance;
    }
}
