package com.ysw.berisaier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_1,btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        btn_1= (Button) findViewById(R.id.btn1);
        btn_2= (Button) findViewById(R.id.btn2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
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
        }
    }
}
