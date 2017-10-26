package com.neusoft.speechdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.neusoft.speechdemo.speech.listener.OnSpeakListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SpeechApplication.getInstance().getSpeech().speak("您好", new OnSpeakListener() {
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
}
