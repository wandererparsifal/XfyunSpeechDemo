package com.neusoft.speechdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SpeechApplication.getInstance().getSpeech().speak("您好，请问有什么可以帮助你的？", new OnSpeakListener() {
            @Override
            public void onSpeakSuccess() {
                SpeechApplication.getInstance().getSpeech().listen(new OnListenListener() {
                    @Override
                    public void onListenSuccess(String pResult) {
                        Log.e(TAG, "pResult " + pResult);
                    }

                    @Override
                    public void onListenError(int pErrorCode) {

                    }
                });
            }

            @Override
            public void onSpeakError(int pErrorCode) {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
