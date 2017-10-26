package com.neusoft.speechdemo.speech.listener;

/**
 * Created by YangMing on 2016/6/29 10:57.
 */
public interface OnSpeakListener {

    void onSpeakSuccess();

    void onSpeakError(int pErrorCode);

    void onCancel();
}