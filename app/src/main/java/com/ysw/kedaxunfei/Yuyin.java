package com.ysw.kedaxunfei;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.ysw.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Swy on 2017/3/30.
 */

public class Yuyin extends AppCompatActivity implements View.OnClickListener {
    Button button,button1;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kedaxunfei_yuyinshibie);
        button= (Button) findViewById(R.id.button);
        button1= (Button) findViewById(R.id.button1);
        editText= (EditText) findViewById(R.id.edit);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                btnVoice();
                break;
            case R.id.button1:
                voiceToText();
                break;
        }
    }

    private static final int REQUEST_PERMISSION_CAMERA_CODE=1;
    private void btnVoice(){
        if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(Yuyin.this,new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_PERMISSION_CAMERA_CODE);
        }
        RecognizerDialog dialog=new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {
                Toast.makeText(Yuyin.this,speechError.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        dialog.show();
    }



    //回调结果：
    private void printResult(RecognizerResult results) {
        //结果是由多段json合起来的
        String text = parseIatResult(results.getResultString());
        // 自动填写地址
        editText.append(text);
    }

    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


    private void voiceToText(){
        SpeechSynthesizer mIts=SpeechSynthesizer.createSynthesizer(Yuyin.this,null);
        mIts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        mIts.setParameter(SpeechConstant.SPEED,"50");
        mIts.setParameter(SpeechConstant.VOLUME,"80");
        mIts.startSpeaking(editText.getText().toString(),mSynListener);
    }
    SynthesizerListener mSynListener=new SynthesizerListener() {
        //开始播放
        @Override
        public void onSpeakBegin() {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }
        //暂停播放
        @Override
        public void onSpeakPaused() {

        }
        //恢复播放回调接口
        @Override
        public void onSpeakResumed() {

        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }
        //会话结束回调接口，没有错误时，error为null
        @Override
        public void onCompleted(SpeechError speechError) {

        }
        //会话事件回调接口
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
}
