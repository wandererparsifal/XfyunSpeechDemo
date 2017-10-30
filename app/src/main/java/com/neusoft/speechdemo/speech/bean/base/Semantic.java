package com.neusoft.speechdemo.speech.bean.base;

import java.util.Arrays;

/**
 * Created by yangming on 17-10-26.
 */
public class Semantic {

    public String intent;

    public Slot[] slots;

    @Override
    public String toString() {
        return "Semantic{" +
                "intent='" + intent + '\'' +
                ", slots=" + Arrays.toString(slots) +
                '}';
    }
}
