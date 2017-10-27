package com.neusoft.speechdemo.speech.listener;

import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

/**
 * Created by YangMing on 2016/6/29 10:58.
 */
public abstract class OnListenListener implements SpeechUnderstanderListener {

    public int requestCode;

    public abstract void onListenSuccess(int requestCode, String pResult);

    public abstract void onListenError(int requestCode, int pErrorCode);

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
        onListenSuccess(requestCode, understanderResult.getResultString());
    }

    @Override
    public void onError(SpeechError speechError) {
        onListenError(requestCode, speechError.getErrorCode());
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

    }
}