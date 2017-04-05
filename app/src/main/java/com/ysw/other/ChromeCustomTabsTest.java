package com.ysw.other;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ysw.R;

/**
 * Created by Swy on 2017/4/5.
 */

public class ChromeCustomTabsTest extends AppCompatActivity {
    private Button button;
    String url="https://www.baidu.com/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        button= (Button) findViewById(R.id.button_chrome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlInChromeCustomTabs(url);
            }
        });
    }
    private void openUrlInChromeCustomTabs(String url){
        CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent=builder.build();
        customTabsIntent.launchUrl(ChromeCustomTabsTest.this, Uri.parse(url));
    }
}
