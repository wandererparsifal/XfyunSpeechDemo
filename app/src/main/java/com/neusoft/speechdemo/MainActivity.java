package com.neusoft.speechdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.neusoft.speechdemo.speech.Speech;
import com.neusoft.speechdemo.speech.bean.Weather;
import com.neusoft.speechdemo.speech.bean.base.Data;
import com.neusoft.speechdemo.speech.bean.base.ListenResult;
import com.neusoft.speechdemo.speech.bean.base.Semantic;
import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.util.JsonUtil;

import static com.neusoft.speechdemo.SpeechID.LISTEN_COMMAND_NAVI;
import static com.neusoft.speechdemo.SpeechID.LISTEN_OPEN_TYPE;
import static com.neusoft.speechdemo.SpeechID.SPEAK_ASK_NAVI;
import static com.neusoft.speechdemo.SpeechID.SPEAK_GREETING;
import static com.neusoft.speechdemo.SpeechID.SPEAK_SORRY;
import static com.neusoft.speechdemo.SpeechID.SPEAK_WEATHER_RESULT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnWake = null;

    /**
     * 语音播报的回调
     */
    private OnSpeakListener mOnSpeakListener = new OnSpeakListener() {
        @Override
        public void onSpeakSuccess(int speakID) {
            Log.d(TAG, "onSpeakSuccess speakID " + speakID);
            switch (speakID) {
                case SPEAK_GREETING:
                    Speech.getInstance().listen(LISTEN_OPEN_TYPE);
                    break;
                case SPEAK_WEATHER_RESULT:
                    Speech.getInstance().speak("需要打开导航软件吗？", SPEAK_ASK_NAVI);
                    break;
                case SPEAK_ASK_NAVI:
                    Speech.getInstance().listen(LISTEN_COMMAND_NAVI);
                    break;
                case SPEAK_SORRY:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSpeakError(int speakID, int errorCode) {
            Log.d(TAG, "onSpeakError speakID " + speakID + ", errorCode " + errorCode);
        }

        @Override
        public void onCancel(int speakID) {
            Log.d(TAG, "onCancel speakID " + speakID);
        }
    };

    /**
     * 语音监听的回调
     */
    private OnListenListener mOnListenListener = new OnListenListener() {
        @Override
        public void onListenSuccess(int listenID, String result) {
            Log.d(TAG, "onListenSuccess listenID " + listenID + ", result " + result);
            switch (listenID) {
                case LISTEN_OPEN_TYPE:
                    // 这里可以有多分支判断，目前只做了天气的判断
                    boolean hasAnswer = false;
                    // 此时不知道 data 具体类型，没有办法直接使用泛型解析
                    ListenResult listenResult = JsonUtil.fromJson(result, ListenResult.class);
                    if (null != listenResult && "weather".equals(listenResult.service)) {
                        // 这里由于此前不知道 listenResult.data 具体类型，没有办法直接按照 Weather[] 类型解析，所以先将 listenResult.data 转回了 Json，
                        // 再转到 Data<Weather[]>，从中取得 Weather[]，如果不想这样做需要手动解析 Json
                        Data<Weather[]> weatherData = JsonUtil.fromJson(JsonUtil.toJson(listenResult.data), new TypeToken<Data<Weather[]>>() {
                        }.getType());
                        if (null != listenResult.answer && null != listenResult.answer.text) {
                            hasAnswer = true;
                            String string = listenResult.answer.text +
                                    weatherData.result[0].airQuality +
                                    "，" +
                                    weatherData.result[0].exp.ct.expName +
                                    weatherData.result[0].exp.ct.level +
                                    "，" +
                                    weatherData.result[0].exp.ct.prompt;
                            Speech.getInstance().speak(string, SPEAK_WEATHER_RESULT);
                        }
                    }
                    if (!hasAnswer) {
                        Speech.getInstance().speak("对不起，没有查询到结果。", SPEAK_SORRY);
                    }
                    break;
                case LISTEN_COMMAND_NAVI:
                    boolean isPositive = false;
                    ListenResult commandResult = JsonUtil.fromJson(result, ListenResult.class);
                    if (null != commandResult && "PNCOMMAND.command".equals(commandResult.service)) {
                        Semantic[] semantics2 = commandResult.semantic;
                        if (null != semantics2 && 0 < semantics2.length) {
                            Semantic semantic = semantics2[0];
                            String intent = semantic.intent;
                            if ("positive".equals(intent)) {
                                Speech.getInstance().speak("正在打开导航", 0);
                                isPositive = true;
                            }
                        }
                    }
                    if (!isPositive) {
                        Speech.getInstance().speak("好的，祝您一路顺风", 0);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onListenError(int listenID, int errorCode) {
            Log.d(TAG, "onListenError listenID " + listenID + ", errorCode " + errorCode);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册播报与监听接口，注册之后 mOnSpeakListener 和 mOnListenListener 才会生效。需要与反注册成对出现
        Speech.getInstance().subscribeOnSpeakListener(mOnSpeakListener);
        Speech.getInstance().subscribeOnListenListener(mOnListenListener);

        mBtnWake = findViewById(R.id.btn_wake);
        mBtnWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speech.getInstance().speak("您好，请问有什么可以帮助您的？", SPEAK_GREETING);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 离开画面时停止播报与监听
        Speech.getInstance().cancelSpeak();
        Speech.getInstance().cancelListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ※ 在 MainActivity、Fragment 等有生命周期的对象中使用时，一定要在合适的时机反注册，不然会内存泄露
        Speech.getInstance().unSubscribeOnSpeakListener(mOnSpeakListener);
        Speech.getInstance().unSubscribeOnListenListener(mOnListenListener);
    }
}
