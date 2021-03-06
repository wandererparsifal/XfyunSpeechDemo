package com.neusoft.speechdemo.speech;

import android.content.Context;

import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.speech.listener.OnSpeechInitListener;

import java.util.ArrayList;

/**
 * 语音实现类
 */
public class Speech {

    private static final String TAG = Speech.class.getSimpleName();

    private SpeechBaseUtil mSpeechBaseUtil = null;

    /**
     * 所有的语音初始化的回调
     */
    private final ArrayList<OnSpeechInitListener> mOnSpeechInitListeners = new ArrayList<>();

    /**
     * 所有的语音播报的回调
     */
    private final ArrayList<OnSpeakListener> mOnSpeakListeners = new ArrayList<>();

    /**
     * 所有的语音监听的回调
     */
    private final ArrayList<OnListenListener> mOnListenListeners = new ArrayList<>();

    private Speech() {
    }

    private static class SingletonHolder {
        private final static Speech INSTANCE = new Speech();
    }

    public static Speech getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化语音功能
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mSpeechBaseUtil = new SpeechBaseUtil();
        mSpeechBaseUtil.init(context, new SpeechBaseUtil.SpeechInitListener() {
            @Override
            public void onCompleted() {
                for (OnSpeechInitListener onSpeechInitListener : mOnSpeechInitListeners) {
                    onSpeechInitListener.onInitSuccess();
                }
            }

            @Override
            public void onError(int code) {
                for (OnSpeechInitListener onSpeechInitListener : mOnSpeechInitListeners) {
                    onSpeechInitListener.onInitError(code);
                }
            }
        });
    }

    public void subscribeOnSpeechInitListener(OnSpeechInitListener listener) {
        synchronized (mOnSpeechInitListeners) {
            mOnSpeechInitListeners.add(listener);
        }
    }

    public void unSubscribeOnSpeechInitListener(OnSpeechInitListener listener) {
        synchronized (mOnSpeechInitListeners) {
            mOnSpeechInitListeners.remove(listener);
        }
    }

    public void subscribeOnSpeakListener(OnSpeakListener listener) {
        synchronized (mOnSpeakListeners) {
            mOnSpeakListeners.add(listener);
        }
    }

    public void unSubscribeOnSpeakListener(OnSpeakListener listener) {
        synchronized (mOnSpeakListeners) {
            mOnSpeakListeners.remove(listener);
        }
    }

    public void subscribeOnListenListener(OnListenListener listener) {
        synchronized (mOnListenListeners) {
            mOnListenListeners.add(listener);
        }
    }

    public void unSubscribeOnListenListener(OnListenListener listener) {
        synchronized (mOnListenListeners) {
            mOnListenListeners.remove(listener);
        }
    }

    /**
     * 语音播报
     *
     * @param text    播报内容
     * @param speakID 播报 ID
     */
    public void speak(String text, int speakID) {
        OnSpeakListener onSpeakListener = new OnSpeakListener() {
            @Override
            public void onSpeakSuccess(int speakID) {
                for (OnSpeakListener onSpeakListener : mOnSpeakListeners) {
                    onSpeakListener.onSpeakSuccess(speakID);
                }
            }

            @Override
            public void onSpeakError(int speakID, int errorCode) {
                for (OnSpeakListener onSpeakListener : mOnSpeakListeners) {
                    onSpeakListener.onSpeakError(speakID, errorCode);
                }
            }

            @Override
            public void onCancel(int speakID) {
                for (OnSpeakListener onSpeakListener : mOnSpeakListeners) {
                    onSpeakListener.onCancel(speakID);
                }
            }
        };
        onSpeakListener.speakID = speakID;
        mSpeechBaseUtil.speak(text, onSpeakListener);
    }

    public boolean isSpeaking() {
        return mSpeechBaseUtil.isSpeaking();
    }

    /**
     * 停止语音播报
     */
    public void cancelSpeak() {
        mSpeechBaseUtil.cancelSpeak();
    }

    /**
     * 语音监听，没有听清将直接返回MSG_ERROR_NO_DATA
     *
     * @param listenID 监听 ID
     */
    public void listen(int listenID) {
        OnListenListener onListenListener = new OnListenListener() {
            @Override
            public void onListenSuccess(int listenID, String result) {
                for (OnListenListener onListenListener : mOnListenListeners) {
                    onListenListener.onListenSuccess(listenID, result);
                }
            }

            @Override
            public void onListenError(int listenID, int errorCode) {
                for (OnListenListener onListenListener : mOnListenListeners) {
                    onListenListener.onListenError(listenID, errorCode);
                }
            }
        };
        onListenListener.listenID = listenID;
        mSpeechBaseUtil.listen(onListenListener);
    }

    public boolean isListening() {
        return mSpeechBaseUtil.isListening();
    }

    /**
     * 停止语音监听
     */
    public void cancelListen() {
        mSpeechBaseUtil.cancelListen();
    }

    /**
     * 停止语音功能，释放语音资源
     */
    public void release() {
        mSpeechBaseUtil.release();
    }
}
