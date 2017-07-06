package com.ysw.other;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Swy on 2017/6/23.
 */

public class GoodHandler extends Activity {
    private  static final  class myHandler extends Handler{
        private  final WeakReference<GoodHandler> mAvtivity;

        public myHandler(GoodHandler avtivity) {
            this.mAvtivity = new WeakReference<GoodHandler>(avtivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GoodHandler activity=mAvtivity.get();
            if (activity!=null) {
                //something
            }
        }
    }

    private final  myHandler handler=new myHandler(this);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(new Runnable() {
            @Override
            public void run() {
                
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        handler.post(new Runnable() {
            @Override
            public void run() {
                //something
            }
        });
    }
}
