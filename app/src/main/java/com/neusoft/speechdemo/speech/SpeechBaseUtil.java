package com.neusoft.speechdemo.speech;

import android.content.Context;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.io.InputStream;

public class SpeechBaseUtil {

    private static final String TAG = SpeechBaseUtil.class.getSimpleName();

    private Context mContext = null;

    private SpeechSynthesizer mSpeaker = null;

    private SpeechRecognizer mGrammarHearer = null;

    private boolean isReaderInitialized = false;

    private boolean isGrammarHearerInitialized = false;

    private boolean isAllInitialized = false;

    private SpeechInitListener mSpeechInitListener = null;

    /**
     * Init SpeechUtil
     *
     * @param pContext
     * @param pCallback
     */
    protected void init(Context pContext, SpeechInitListener pCallback) {
        this.mContext = pContext;
        this.mSpeechInitListener = pCallback;
        this.mSpeaker = SpeechSynthesizer.createSynthesizer(pContext, new InitListener() {
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
        this.mGrammarHearer = SpeechRecognizer.createRecognizer(pContext, new InitListener() {
            @Override
            public void onInit(int code) {
                if (code == ErrorCode.SUCCESS) {
                    isGrammarHearerInitialized = true;
                    checkInit();
                    Log.d(TAG, "GrammarHearer init success");
                } else {
                    mSpeechInitListener.onError(code);
                    Log.e(TAG, "GrammarHearer init error，code = " + code);
                }
            }
        });
    }

    private void checkInit() {
        Log.d(TAG, "checkInit");
        if (isReaderInitialized && isGrammarHearerInitialized) {
            isAllInitialized = true;
            Log.d(TAG, "checkInit AllInitialized");
            mSpeechInitListener.onCompleted();
        }
    }

    private String readFile(Context mContext, String file, String code) {
        int len;
        byte[] buf;
        String result = "";
        try {
            InputStream in = mContext.getAssets().open(file);
            len = in.available();
            buf = new byte[len];
            in.read(buf, 0, len);
            result = new String(buf, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Text To Speech
     *
     * @param pText
     * @param pSpeakListener
     */
    protected void speak(String pText, SynthesizerListener pSpeakListener) {
        if (isAllInitialized) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
            setSpeakParam();
            mSpeaker.startSpeaking(pText, pSpeakListener);
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

    protected void commonHear(final RecognizerListener pHearListener) {
        if (isAllInitialized) {
            setCommonHearParam();
            int code = mGrammarHearer.startListening(pHearListener);
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "start commonHear error : " + code);
            } else {
                Log.d(TAG, "start commonHear success");
            }
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    protected boolean isHearing() {
        if (isAllInitialized) {
            return mGrammarHearer.isListening();
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
            return false;
        }
    }

    protected void cancelSpeaking() {
        if (isAllInitialized) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
        } else {
            Log.e(TAG, "SpeechUtil is uninitialized.");
        }
    }

    protected void cancelHearing() {
        if (isAllInitialized) {
            if (mGrammarHearer.isListening()) {
                mGrammarHearer.cancel();
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
     * Free Speaker And Hearer
     */
    protected void release() {
        if (null != mSpeaker) {
            if (mSpeaker.isSpeaking()) {
                mSpeaker.stopSpeaking();
            }
            mSpeaker.destroy();
        }
        if (null != mGrammarHearer) {
            if (mGrammarHearer.isListening()) {
                mGrammarHearer.cancel();
            }
            mGrammarHearer.destroy();
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

    public void setCommonHearParam() {
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.PARAMS, null);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.ENGINE_TYPE, com.iflytek.cloud.SpeechConstant.TYPE_CLOUD);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.RESULT_TYPE, SpeechConstant.RESULT_TYPE);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.LANGUAGE, SpeechConstant.LANGUAGE);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.ACCENT, SpeechConstant.ACCENT);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.VAD_BOS, SpeechConstant.VAD_BOS);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.VAD_EOS, SpeechConstant.VAD_EOS);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.ASR_PTT, SpeechConstant.ASR_PTT);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.AUDIO_FORMAT, SpeechConstant.AUDIO_FORMAT);
        mGrammarHearer.setParameter(com.iflytek.cloud.SpeechConstant.ASR_AUDIO_PATH, SpeechConstant.IAT_AUDIO_PATH);
    }

    public interface SpeechInitListener {

        void onCompleted();

        void onError(int code);
    }
}
