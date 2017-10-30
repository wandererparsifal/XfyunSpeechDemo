package com.neusoft.speechdemo.speech.listener;

import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

/**
 * Created by YangMing on 2016/6/29 10:58.
 */
public abstract class OnListenListener implements SpeechUnderstanderListener {

    public int listenID;

    public abstract void onListenSuccess(int listenID, String result);

    public abstract void onListenError(int listenID, int errorCode);

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
        onListenSuccess(listenID, understanderResult.getResultString());
    }

    @Override
    public void onError(SpeechError speechError) {
        onListenError(listenID, speechError.getErrorCode());
    }

    @Override
    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

    }
}