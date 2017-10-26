package com.neusoft.speechdemo;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
import com.neusoft.speechdemo.speech.Speech;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

/**
 * Created by yangming on 17-10-26.
 */
public class SpeechApplication extends Application {

    private static SpeechApplication instance;

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        super.onCreate();
        instance = this;
        Speech.getInstance().init(this, new OnSpeechInitListener() {
            @Override
            public void onInitSuccess() {
                Speech.getInstance().speak("讯飞语音初始化成功", new OnSpeakListener() {
                    @Override
                    public void onSpeakSuccess() {

                    }

                    @Override
                    public void onSpeakError(int pErrorCode) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }

            @Override
            public void onInitError(int pErrorCode) {

            }
        });
    }

    public static SpeechApplication getInstance() {
        return instance;
    }
}
