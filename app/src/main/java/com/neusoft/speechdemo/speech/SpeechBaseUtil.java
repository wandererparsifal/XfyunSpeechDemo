package com.neusoft.speechdemo.speech;

import android.content.Context;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.SynthesizerListener;

public class SpeechBaseUtil {

    private static final String TAG = SpeechBaseUtil.class.getSimpleName();

    private SpeechSynthesizer mSpeaker = null;

    private SpeechUnderstander mListener = null;

    private boolean isReaderInitialized = false;

    private boolean isListenerInitialized = false;

    private boolean isAllInitialized = false;

    private SpeechInitListener mSpeechInitListener = null;

    /**
     * Init SpeechUtil
     *
     * @param context
     * @param callback
     */
    protected void init(Context context, SpeechInitListener callback) {
        this.mSpeechInitListener = callback;
        this.mSpeaker = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code == ErrorCode.SUCCESS) {
                    isReaderInitialized = true;
                    checkInit();
                    Log.d(TAG, "Speaker init success");
                } else {
                    mSpeechInitListener.onError(code);
                    Log.e(TAG, "Speaker init error，code = " + code);
                }
            }
        });
        this.mListener = SpeechUnderstander.createUnderstander(context, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code == ErrorCode.SUCCESS) {
                    isListenerInitialized = true;
                    checkInit();
                    Log.d(TAG, "Listener init success");
                } else {
                    mSpeechInitListener.onError(code);
                    Log.e(TAG, "Listener init error，code = " + code);
                }
            }
        });
    }

    private void checkInit() {
        Log.d(TAG, "checkInit");
        if (isReaderInitialized && isListenerInitialized) {
            isAllInitialized = true;
            Log.d(TAG, "checkInit AllInitialized");
            mSpeechInitListener.onCompleted();
        }
    }

    /**
     * Text To Speech
     *
     * @param text
     * @param speakListener
     */
    protected void speak(String text, SynthesizerListener speakListener) {
        if (isAllInitialized) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
            setSpeakParam();
            mSpeaker.startSpeaking(text, speakListener);
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    protected boolean isSpeaking() {
        if (isAllInitialized) {
            return mSpeaker.isSpeaking();
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
            return false;
        }
    }

    protected void listen(final SpeechUnderstanderListener listenListener) {
        if (isAllInitialized) {
            setListenParam();
            int code = mListener.startUnderstanding(listenListener);
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "start listen error : " + code);
            } else {
                Log.d(TAG, "start listen success");
            }
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    protected boolean isListening() {
        if (isAllInitialized) {
            return mListener.isUnderstanding();
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
            return false;
        }
    }

    protected void cancelSpeak() {
        if (isAllInitialized) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    protected void cancelListen() {
        if (isAllInitialized) {
            if (mListener.isUnderstanding()) {
                mListener.cancel();
            }
            /**
             * cancel是停止识别，调用后，不会录音，也不会返回结果了，直接终止了识别
             * stopListening是停止录音，调用之后，已经被录下来的音频数据还是会上传，返回识别结果
             */
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    /**
     * Free Speaker And Listener
     */
    protected void release() {
        if (null != mSpeaker) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
            mSpeaker.destroy();
        }
        if (null != mListener) {
            if (mListener.isUnderstanding()) {
                mListener.cancel();
            }
            mListener.destroy();
        }
    }

    private void setSpeakParam() {
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.PARAMS, null);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.ENGINE_TYPE, com.iflytek.cloud.SpeechConstant.TYPE_CLOUD);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.VOICE_NAME, SpeechConstant.VOICE_NAME);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.SPEED, SpeechConstant.SPEED);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.PITCH, SpeechConstant.PITCH);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.VOLUME, SpeechConstant.VOLUME);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.STREAM_TYPE, SpeechConstant.STREAM_TYPE);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.KEY_REQUEST_FOCUS, SpeechConstant.KEY_REQUEST_FOCUS);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.AUDIO_FORMAT, SpeechConstant.AUDIO_FORMAT);
        mSpeaker.setParameter(com.iflytek.cloud.SpeechConstant.TTS_AUDIO_PATH, SpeechConstant.TTS_AUDIO_PATH);
    }

    public void setListenParam() {
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.PARAMS, null);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.ENGINE_TYPE, com.iflytek.cloud.SpeechConstant.TYPE_CLOUD);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.RESULT_TYPE, SpeechConstant.RESULT_TYPE);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.LANGUAGE, SpeechConstant.LANGUAGE);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.ACCENT, SpeechConstant.ACCENT);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.VAD_BOS, SpeechConstant.VAD_BOS);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.VAD_EOS, SpeechConstant.VAD_EOS);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.ASR_PTT, SpeechConstant.ASR_PTT);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.AUDIO_FORMAT, SpeechConstant.AUDIO_FORMAT);
        mListener.setParameter(com.iflytek.cloud.SpeechConstant.ASR_AUDIO_PATH, SpeechConstant.IAT_AUDIO_PATH);
    }

    public interface SpeechInitListener {

        void onCompleted();

        void onError(int code);
    }
}
