package com.neusoft.speechdemo.speech.bean.base;

/**
 * Created by yangming on 17-10-26.
 */
public class Data<T> {

    public String header;

    public T result;

    @Override
    public String toString() {
        return "Data{" +
                "header='" + header + '\'' +
                ", result=" + result +
                '}';
    }
}
