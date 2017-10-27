package com.neusoft.speechdemo.speech.listener;

/**
 * Created by yangming on 17-5-3.
 */
public interface OnSpeechInitListener {

    void onInitSuccess();

    void onInitError(int errorCode);
}
