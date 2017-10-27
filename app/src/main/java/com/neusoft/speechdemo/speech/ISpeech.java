package com.neusoft.speechdemo.speech;

import android.content.Context;

import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

/**
 * 语音接口
 * Created by yangming on 17-4-21.
 */
public interface ISpeech {

    /**
     * 初始化语音功能
     *
     * @param pContext              上下文
     * @param pOnSpeechInitListener 语音功能初始化回调
     */
    void init(Context pContext, OnSpeechInitListener pOnSpeechInitListener);

    /**
     * 语音播报
     *
     * @param text        播报内容
     * @param requestCode 播报请求码
     */
    void speak(String text, int requestCode);

    boolean isSpeaking();

    /**
     * 停止语音播报
     */
    void cancelSpeak();

    /**
     * 只会听一次的语音监听，没有听清将直接返回MSG_ERROR_NO_DATA
     *
     * @param pOnListenListener 监听回调
     */
    void listen(OnListenListener pOnListenListener);

    boolean isListening();

    /**
     * 停止语音监听
     */
    void cancelListen();

    /**
     * 停止语音功能，释放语音资源
     */
    void release();
}
