package com.neusoft.speechdemo.speech;

import android.content.Context;
import android.util.Log;

import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

/**
 * 语音实现类
 */
public class Speech implements ISpeech {

    private static final String TAG = Speech.class.getSimpleName();

    private SpeechBaseUtil mSpeechBaseUtil = null;

    private Context mContext = null;

    private Speech() {
    }

    private static class SingletonHolder {
        private final static Speech INSTANCE = new Speech();
    }

    public static Speech getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void init(Context pContext, final OnSpeechInitListener pOnSpeechInitListener) {
        mContext = pContext;
        mSpeechBaseUtil = new SpeechBaseUtil();
        mSpeechBaseUtil.init(mContext, new SpeechBaseUtil.SpeechInitListener() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "SpeechBaseUtil init Completed");
                pOnSpeechInitListener.onInitSuccess();
            }

            @Override
            public void onError(int code) {
                Log.d(TAG, "SpeechBaseUtil init Error, ErrorCode = " + code);
                pOnSpeechInitListener.onInitError(code);
            }
        });
    }

    @Override
    public void speak(String pText, OnSpeakListener pOnSpeakListener) {
        mSpeechBaseUtil.speak(pText, pOnSpeakListener);
    }

    @Override
    public boolean isSpeaking() {
        return mSpeechBaseUtil.isSpeaking();
    }

    @Override
    public void cancelSpeak() {
        mSpeechBaseUtil.cancelSpeak();
    }

    @Override
    public void listen(OnListenListener pOnListenListener) {
        mSpeechBaseUtil.listen(pOnListenListener);
    }

    @Override
    public boolean isListening() {
        return mSpeechBaseUtil.isListening();
    }

    @Override
    public void cancelListen() {
        mSpeechBaseUtil.cancelListen();
    }

    @Override
    public void release() {
        mSpeechBaseUtil.release();
    }
}
