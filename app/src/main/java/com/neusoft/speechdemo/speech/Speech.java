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

    /**
     * 同一时间只能有一个语音播报，只需要一个实例
     */
    private OnSpeakListener mOnSpeakListener = null;

    /**
     * 同一时间只能有一个语音监听，只需要一个实例
     */
    private OnListenListener mOnListenListener = null;

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
        mSpeechBaseUtil = new SpeechBaseUtil();
        mSpeechBaseUtil.init(pContext, new SpeechBaseUtil.SpeechInitListener() {
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

    public void subscribeOnSpeakListener(OnSpeakListener listener) {
        mOnSpeakListener = listener;
    }

    public void unSubscribeOnSpeakListener(OnSpeakListener listener) {
        mOnSpeakListener = null;
    }

    public void subscribeOnListenListener(OnListenListener listener) {
        mOnListenListener = listener;
    }

    public void unSubscribeOnListenListener(OnListenListener listener) {
        mOnListenListener = null;
    }

    @Override
    public void speak(String text, int requestCode) {
        if (null == mOnSpeakListener) { // 防止空指针
            mOnSpeakListener = new OnSpeakListener() {
                @Override
                public void onSpeakSuccess(int requestCode) {

                }

                @Override
                public void onSpeakError(int requestCode, int pErrorCode) {

                }

                @Override
                public void onCancel(int requestCode) {

                }
            };
        }
        mOnSpeakListener.requestCode = requestCode;
        mSpeechBaseUtil.speak(text, mOnSpeakListener);
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
    public void listen(int requestCode) {
        if (null == mOnListenListener) { // 防止空指针
            mOnListenListener = new OnListenListener() {
                @Override
                public void onListenSuccess(int requestCode, String pResult) {

                }

                @Override
                public void onListenError(int requestCode, int pErrorCode) {

                }
            };
        }
        mOnListenListener.requestCode = requestCode;
        mSpeechBaseUtil.listen(mOnListenListener);
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
