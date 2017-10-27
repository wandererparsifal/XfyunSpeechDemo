package com.neusoft.speechdemo;

/**
 * Created by yangming on 17-10-27.
 */
public class RequestCode {

    /**
     * "您好，请问有什么可以帮助您的？"
     */
    public static final int SPEAK_GREETING = 10001;

    /**
     * 说天气结果
     */
    public static final int SPEAK_WEATHER_RESULT = 10002;

    /**
     * "对不起，没有查询到结果。"
     */
    public static final int SPEAK_SORRY = 10003;

    /**
     * 开放式，无特定期待结果
     */
    public static final int LISTEN_OPEN_TYPE = 20001;
}
