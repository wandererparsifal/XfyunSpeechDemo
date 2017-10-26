package com.neusoft.speechdemo.speech.bean;

/**
 * Created by yangming on 17-10-26.
 */
public class Answer {

    public String type;

    public String text;

    public String imgUrl;

    public String imgDesc;

    public String url;

    public String urlDesc;

    public String emotion;

    @Override
    public String toString() {
        return "Answer{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", url='" + url + '\'' +
                ", urlDesc='" + urlDesc + '\'' +
                ", emotion='" + emotion + '\'' +
                '}';
    }
}
