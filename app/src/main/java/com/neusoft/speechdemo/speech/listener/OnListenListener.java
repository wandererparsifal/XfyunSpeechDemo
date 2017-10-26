package com.neusoft.speechdemo.speech.listener;

/**
 * Created by YangMing on 2016/6/29 10:58.
 */
public interface OnListenListener {

    void onListenSuccess(String pResult);

    void onListenError(int pErrorCode);
}