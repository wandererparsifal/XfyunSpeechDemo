package com.neusoft.speechdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.neusoft.speechdemo.speech.Speech;
import com.neusoft.speechdemo.speech.bean.ListenResult;
import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.util.JsonUtil;

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
        Speech.getInstance().speak("您好，请问有什么可以帮助您的？", new OnSpeakListener() {
            @Override
            public void onSpeakSuccess() {
                Speech.getInstance().listen(new OnListenListener() {
                    @Override
                    public void onListenSuccess(String pResult) {
                        Log.e(TAG, "pResult " + pResult);
                        ListenResult listenResult = JsonUtil.fromJson(pResult, new TypeToken<ListenResult>() {
                        }.getType());
                        Log.e(TAG, "listenResult " + pResult);
                        if (null != listenResult && null != listenResult.answer && null != listenResult.answer.text) {
                            Speech.getInstance().speak(listenResult.answer.text, new OnSpeakListener() {
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
                        } else {
                            Speech.getInstance().speak("对不起，没有查询到结果", new OnSpeakListener() {
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
