package com.ysw.berisaier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ysw.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_1,btn_2,btn_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.berisaier_main);
        initView();
    }
    private void initView(){
        btn_1= (Button) findViewById(R.id.btn1);
        btn_2= (Button) findViewById(R.id.btn2);
        btn_3= (Button) findViewById(R.id.btn3);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Intent intent1=new Intent(MainActivity.this,Act1.class);
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2=new Intent(MainActivity.this,Act2.class);
                startActivity(intent2);
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this,Act3.class));
        }
    }
}
