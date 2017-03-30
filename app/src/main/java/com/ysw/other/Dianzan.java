package com.ysw.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wx.goodview.GoodView;
import com.ysw.R;

/**
 * Created by Swy on 2017/3/29.
 *  GoodView开源项目 https://github.com/venshine/GoodView
 */

public class Dianzan extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dianzan);
        button= (Button) findViewById(R.id.btn_dianzan);
        final GoodView goodView=new GoodView(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodView.setText("+1");
                goodView.show(v);
            }
        });
    }
}
