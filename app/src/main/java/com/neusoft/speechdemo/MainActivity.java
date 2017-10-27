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

    private OnSpeakListener mOnSpeakListener = new OnSpeakListener() {
        @Override
        public void onSpeakSuccess(int requestCode) {
            Log.d(TAG, "onSpeakSuccess requestCode " + requestCode);
            switch (requestCode) {
                case 101:
                    Speech.getInstance().listen(201);
                    break;
                case 102:
                    break;
                case 103:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSpeakError(int requestCode, int pErrorCode) {
            Log.d(TAG, "onSpeakError requestCode " + requestCode + ", pErrorCode " + pErrorCode);
        }

        @Override
        public void onCancel(int requestCode) {
            Log.d(TAG, "onCancel requestCode " + requestCode);
        }
    };

    private OnListenListener mOnListenListener = new OnListenListener() {
        @Override
        public void onListenSuccess(int requestCode, String pResult) {
            Log.d(TAG, "onListenSuccess requestCode " + requestCode);
            switch (requestCode) {
                case 201:
                    Log.e(TAG, "pResult " + pResult);
                    ListenResult listenResult = JsonUtil.fromJson(pResult, new TypeToken<ListenResult>() {
                    }.getType());
                    Log.e(TAG, "listenResult " + pResult);
                    if (null != listenResult && null != listenResult.answer && null != listenResult.answer.text) {
                        Speech.getInstance().speak(listenResult.answer.text, 102);
                    } else {
                        Speech.getInstance().speak("对不起，没有查询到结果", 103);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onListenError(int requestCode, int pErrorCode) {
            Log.d(TAG, "onListenError requestCode " + requestCode + ", pErrorCode " + pErrorCode);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Speech.getInstance().subscribeOnSpeakListener(mOnSpeakListener);
        Speech.getInstance().subscribeOnListenListener(mOnListenListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Speech.getInstance().speak("您好，请问有什么可以帮助您的？", 101);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Speech.getInstance().cancelSpeak();
        Speech.getInstance().cancelListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ※ 在 MainActivity、Fragment 等有生命周期的对象中使用时，一定要在合适的时机反注册，不然会内存泄露
        Speech.getInstance().unSubscribeOnSpeakListener(mOnSpeakListener);
        Speech.getInstance().unSubscribeOnListenListener(mOnListenListener);
    }
}
