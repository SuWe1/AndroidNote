package com.ysw.rxjavaretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ysw.R;
import com.ysw.rxjavaretrofit.base.BaseActicity;
import com.ysw.rxjavaretrofit.flatmap.FlatMapActivity;
import com.ysw.rxjavaretrofit.map.MapActivity;

/**
 * Created by Swy on 2017/3/28.
 * retrofit基本使用
 * retrofit结合rxjava的使用 基本请求 map flatmap
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1,btn2,btn3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_retrofit_main);
        btn1= (Button) findViewById(R.id.btn1);
        btn2= (Button) findViewById(R.id.btn2);
        btn3= (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                startActivity(new Intent(this, BaseActicity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, FlatMapActivity.class));
                break;
        }
    }
}
