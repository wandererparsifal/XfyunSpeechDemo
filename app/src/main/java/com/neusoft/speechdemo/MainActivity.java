package com.neusoft.speechdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.neusoft.speechdemo.speech.Speech;
import com.neusoft.speechdemo.speech.bean.ListenResult;
import com.neusoft.speechdemo.speech.bean.Semantic;
import com.neusoft.speechdemo.speech.bean.Weather;
import com.neusoft.speechdemo.speech.listener.OnListenListener;
import com.neusoft.speechdemo.speech.listener.OnSpeakListener;
import com.neusoft.speechdemo.util.JsonUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.neusoft.speechdemo.RequestCode.LISTEN_OPEN_TYPE;
import static com.neusoft.speechdemo.RequestCode.LISTEN_PNCOMMAND_NAVI;
import static com.neusoft.speechdemo.RequestCode.SPEAK_ASK_NAVI;
import static com.neusoft.speechdemo.RequestCode.SPEAK_GREETING;
import static com.neusoft.speechdemo.RequestCode.SPEAK_SORRY;
import static com.neusoft.speechdemo.RequestCode.SPEAK_WEATHER_RESULT;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks { // Android 6.0 以上 Permission 特殊处理

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnWake = null;

    private boolean hasAllPermissions = false;

    /**
     * 所要申请的权限
     */
    private String[] mPermissions = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 语音播报的回调
     */
    private OnSpeakListener mOnSpeakListener = new OnSpeakListener() {
        @Override
        public void onSpeakSuccess(int requestCode) {
            Log.d(TAG, "onSpeakSuccess requestCode " + requestCode);
            switch (requestCode) {
                case SPEAK_GREETING:
                    Speech.getInstance().listen(LISTEN_OPEN_TYPE);
                    break;
                case SPEAK_WEATHER_RESULT:
                    Speech.getInstance().speak("需要打开导航软件吗？", SPEAK_ASK_NAVI);
                    break;
                case SPEAK_ASK_NAVI:
                    Speech.getInstance().listen(LISTEN_PNCOMMAND_NAVI);
                    break;
                case SPEAK_SORRY:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSpeakError(int requestCode, int pErrorCode) {
            Log.d(TAG, "onSpeakError requestCode " + requestCode + ", pErrorCode " + pErrorCode);
        }

        @Override
        public void onCancel(int requestCode) {
            Log.d(TAG, "onCancel requestCode " + requestCode);
        }
    };

    /**
     * 语音监听的回调
     */
    private OnListenListener mOnListenListener = new OnListenListener() {
        @Override
        public void onListenSuccess(int requestCode, String pResult) {
            Log.d(TAG, "onListenSuccess requestCode " + requestCode + ", pResult " + pResult);
            switch (requestCode) {
                case LISTEN_OPEN_TYPE:
                    // 这里可以有多分支判断，目前只做了天气的判断
                    boolean hasAnswer = false;
                    ListenResult<Weather[]> weatherResult = JsonUtil.fromJson(pResult, new TypeToken<ListenResult<Weather[]>>() {
                    }.getType());
                    if (null != weatherResult && "weather".equals(weatherResult.service)) {
                        if (null != weatherResult.answer && null != weatherResult.answer.text) {
                            hasAnswer = true;
                            String string = weatherResult.answer.text +
                                    weatherResult.data.result[0].airQuality +
                                    "，" +
                                    weatherResult.data.result[0].exp.ct.expName +
                                    weatherResult.data.result[0].exp.ct.level +
                                    "，" +
                                    weatherResult.data.result[0].exp.ct.prompt;
                            Speech.getInstance().speak(string, SPEAK_WEATHER_RESULT);
                        }
                    }
                    if (!hasAnswer) {
                        Speech.getInstance().speak("对不起，没有查询到结果。", SPEAK_SORRY);
                    }
                    break;
                case LISTEN_PNCOMMAND_NAVI:
                    boolean isPositive = false;
                    ListenResult commandResult = JsonUtil.fromJson(pResult, new TypeToken<ListenResult>() {
                    }.getType());
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
        public void onListenError(int requestCode, int pErrorCode) {
            Log.d(TAG, "onListenError requestCode " + requestCode + ", pErrorCode " + pErrorCode);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (EasyPermissions.hasPermissions(this, mPermissions)) { // 检查是否获取该权限
            Log.d(TAG, "已获取权限");
            hasAllPermissions = true;
        } else {
            // 第二个参数是被拒绝后再次申请该权限的解释
            // 第三个参数是请求码
            // 第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "必要的权限未被授权，请授权", 0, mPermissions);
        }

        // 注册播报与监听接口，注册之后 mOnSpeakListener 和 mOnListenListener 才会生效。需要与反注册成对出现
        Speech.getInstance().subscribeOnSpeakListener(mOnSpeakListener);
        Speech.getInstance().subscribeOnListenListener(mOnListenListener);

        mBtnWake = findViewById(R.id.btn_wake);
        mBtnWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAllPermissions) {
                    Speech.getInstance().speak("您好，请问有什么可以帮助您的？", SPEAK_GREETING);
                } else {
                    Speech.getInstance().speak("请授权全部权限后使用", 0);
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // 下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口
    // 分别返回授权成功和失败的权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "获取成功的权限" + perms);
        if (perms.size() == mPermissions.length) {
            hasAllPermissions = true;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "获取失败的权限" + perms);
    }
}
