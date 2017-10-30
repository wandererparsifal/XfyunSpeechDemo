package com.neusoft.speechdemo.speech;

import android.content.Context;

/**
 * 语音接口
 * Created by yangming on 17-4-21.
 */
public interface ISpeech {

    /**
     * 初始化语音功能
     *
     * @param context 上下文
     */
    void init(Context context);

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
     * 语音监听，没有听清将直接返回MSG_ERROR_NO_DATA
     *
     * @param requestCode 监听请求码
     */
    void listen(int requestCode);

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
