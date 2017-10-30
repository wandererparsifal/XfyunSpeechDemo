package com.neusoft.speechdemo.speech.bean.base;

import java.util.Arrays;

/**
 * Created by yangming on 17-10-26.
 */
public class ListenResult<T> {

    public int rc;

    public Error error;

    public String text;

    public String vendor;

    public String service;

    public Semantic[] semantic;

    public Data<T> data;

    public Answer answer;

    public String dialog_stat;

    public MoreResult[] moreResults;

    @Override
    public String toString() {
        return "ListenResult{" +
                "rc=" + rc +
                ", error=" + error +
                ", text='" + text + '\'' +
                ", vendor='" + vendor + '\'' +
                ", service='" + service + '\'' +
                ", semantic=" + Arrays.toString(semantic) +
                ", data=" + data +
                ", answer=" + answer +
                ", dialog_stat='" + dialog_stat + '\'' +
                ", moreResults=" + Arrays.toString(moreResults) +
                '}';
    }
}
