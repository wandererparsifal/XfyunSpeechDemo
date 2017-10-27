package com.neusoft.speechdemo.speech.bean;

/**
 * Created by yangming on 17-10-27.
 */
public class Weather {

    public int airData;

    public String airQuality;

    public String city;

    public String date;

    public int dateLong;

    public ExpBean exp;

    public String humidity;

    public String lastUpdateTime;

    public String pm25;

    public int temp;

    public String tempRange;

    public String weather;

    public int weatherType;

    public String wind;

    public int windLevel;

    public static class ExpBean {

        public CtBean ct;

        public static class CtBean {

            public String expName;

            public String level;

            public String prompt;
        }
    }
}
