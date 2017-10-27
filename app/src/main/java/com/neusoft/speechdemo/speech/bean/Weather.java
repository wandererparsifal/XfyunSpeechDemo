package com.neusoft.speechdemo.speech.bean;

/**
 * Created by yangming on 17-10-27.
 */
public class Weather {

    public String airData;

    public String airQuality;

    public String city;

    public String date;

    public String dateLong;

    public ExpBean exp;

    public String humidity;

    public String lastUpdateTime;

    public String pm25;

    public String temp;

    public String tempRange;

    public String weather;

    public String weatherType;

    public String wind;

    public String windLevel;

    public static class ExpBean {

        public CtBean ct;

        public static class CtBean {

            public String expName;

            public String level;

            public String prompt;
        }
    }
}
