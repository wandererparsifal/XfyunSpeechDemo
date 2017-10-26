package com.neusoft.speechdemo.speech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.UnderstanderResult;
import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

import static com.iflytek.cloud.SpeechEvent.EVENT_TTS_CANCEL;

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
    public void speak(String pText, final OnSpeakListener pOnSpeakListener) {
        mSpeechBaseUtil.speak(pText, new SynthesizerListener() {

            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int percent, int beginPos, int endPos) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                if (null == speechError) {
                    pOnSpeakListener.onSpeakSuccess();
                } else {
                    pOnSpeakListener.onSpeakError(speechError.getErrorCode());
                }
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                if (EVENT_TTS_CANCEL == eventType) {
                    pOnSpeakListener.onCancel();
                }
            }
        });
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
    public void listen(final OnListenListener pOnListenListener) {
        mSpeechBaseUtil.listen(new SpeechUnderstanderListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onResult(UnderstanderResult understanderResult) {
                pOnListenListener.onListenSuccess(understanderResult.getResultString());
            }

            @Override
            public void onError(SpeechError speechError) {
                pOnListenListener.onListenError(speechError.getErrorCode());
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
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
