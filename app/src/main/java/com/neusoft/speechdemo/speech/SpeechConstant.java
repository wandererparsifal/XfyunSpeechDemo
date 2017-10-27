package com.neusoft.speechdemo.speech;

import android.os.Environment;

/**
 * 语音常量
 */
public class SpeechConstant {
    /**
     * Speaker.
     */
    public static final String VOICE_NAME = "xiaoqi";
    /**
     * Speech speed.
     */
    public static final String SPEED = "60";
    /**
     * Pitch.
     */
    public static final String PITCH = "50";
    /**
     * Volume.
     */
    public static final String VOLUME = "50";
    /**
     * Audio stream type.
     */
    public static final String STREAM_TYPE = "3";
    /**
     * Break music.
     */
    public static final String KEY_REQUEST_FOCUS = "true";
    /**
     * Audio format.
     */
    public static final String AUDIO_FORMAT = "wav";
    /**
     * Language.
     */
    public static final String LANGUAGE = "zh_cn";
    /**
     * Accent.
     */
    public static final String ACCENT = "mandarin";
    /**
     * Mute timeout before speak.
     */
    public static final String VAD_BOS = "3000";
    /**
     * Mute timeout after speak.
     */
    public static final String VAD_EOS = "1000";
    /**
     * Whether to use punctuation.
     */
    public static final String ASR_PTT = "1";
    /**
     * Text encoding.
     */
    public static final String TEXT_ENCODING = "utf-8";
    /**
     * Result type.
     */
    public static final String RESULT_TYPE = "json";
    /**
     * Recognition threshold.
     */
    public static final String ASR_THRESHOLD = "30";
    /**
     * TTS Audio save path.
     */
    public static final String TTS_AUDIO_PATH = Environment.getExternalStorageDirectory() + "/msc/tts.wav";
    /**
     * IAT save path.
     */
    public static final String IAT_AUDIO_PATH = Environment.getExternalStorageDirectory() + "/msc/iat.wav";
    /**
     * ASR save path.
     */
    public static final String GRM_AUDIO_PATH = Environment.getExternalStorageDirectory() + "/msc/asr.wav";
    /**
     * UNDERSTAND Audio save path.
     */
    public static final String USD_AUDIO_PATH = Environment.getExternalStorageDirectory() + "/msc/understand.wav";
    /**
     * LOCAL Grammar path.
     */
    public static final String LOCAL_GRM_PATH = Environment.getExternalStorageDirectory() + "/msc/grm";
    /**
     * IVW Threshold.
     */
    public static final String IVW_THRESHOLD = "0:10";
    /**
     * IVW SST.
     */
    public static final String IVW_SST = "wakeup";
    /**
     * KEEP alive.
     */
    public static final String KEEP_ALIVE = "0";
    /**
     * IVW net mode.
     */
    public static final String IVW_NET_MODE = "0";
}
