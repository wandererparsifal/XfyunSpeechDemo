package com.neusoft.speechdemo.speech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

import static com.iflytek.cloud.SpeechEvent.EVENT_TTS_CANCEL;

/**
 * 语音实现类
 * Created by yangming on 17-4-24.
 */
public class SpeechImpl implements ISpeech {

    private static final String TAG = SpeechImpl.class.getSimpleName();

    private SpeechBaseUtil mSpeechBaseUtil = null;

    private Context mContext = null;

    public SpeechImpl(Context pContext) {
        mContext = pContext;
        mSpeechBaseUtil = new SpeechBaseUtil();
    }

    @Override
    public void init(final OnSpeechInitListener pOnSpeechInitListener) {
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
        mSpeechBaseUtil.cancelSpeaking();
    }

    @Override
    public void listen(final OnListenListener pOnListenListener) {
        mSpeechBaseUtil.commonHear(new RecognizerListener() {
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
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                pOnListenListener.onListenSuccess(recognizerResult.getResultString());
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
        return mSpeechBaseUtil.isHearing();
    }

    @Override
    public void cancelListen() {
        mSpeechBaseUtil.cancelHearing();
    }

    @Override
    public void release() {
        mSpeechBaseUtil.release();
    }
}
