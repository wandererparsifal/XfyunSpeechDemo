package com.neusoft.speechdemo.speech.listener;

import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

import static com.iflytek.cloud.SpeechEvent.EVENT_TTS_CANCEL;

/**
 * Created by YangMing on 2016/6/29 10:57.
 */
public abstract class OnSpeakListener implements SynthesizerListener {

    public int speakID;

    public abstract void onSpeakSuccess(int speakID);

    public abstract void onSpeakError(int speakID, int errorCode);

    public abstract void onCancel(int speakID);

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
            onSpeakSuccess(speakID);
        } else {
            onSpeakError(speakID, speechError.getErrorCode());
        }
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        if (EVENT_TTS_CANCEL == eventType) {
            onCancel(speakID);
        }
    }
}