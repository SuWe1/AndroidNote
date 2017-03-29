package com.ysw.rxjavaretrofit.flatmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ysw.R;
import com.ysw.rxjavaretrofit.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Swy on 2017/3/28.
 */

public class FlatMapActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_retrofit_flatmap);
        button= (Button) findViewById(R.id.btn_flatmap);
        textView= (TextView) findViewById(R.id.tv_flatmap);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        load();
    }

    /**
     * flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。
     * 但需要注意，和 map() 不同的是， flatMap() 中返回的是个 Observable 对象，
     * 并且这个 Observable 对象并不是被直接发送到了 Subscriber/Observer 的回调方法中。
     * flatMap() 的原理是这样的：
     * 1. 使用传入的事件对象创建一个 Observable 对象；
     * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
     * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber/Observer的回调方法。
     * 这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。
     * 而这个『铺平』就是 flatMap() 所谓的 flat。
     */
    private void load() {
        final FakeApi fakeApi = Network.getFakeApi();
        fakeApi.getFakeToken("fake_auth_code")
                .flatMap(new Func1<FakeToken, Observable<FakeThing>>() {
                    @Override
                    public Observable<FakeThing> call(FakeToken fakeToken) {
                        return fakeApi.getFakeThing(fakeToken);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FakeThing>() {
                    @Override
                    public void call(FakeThing fakeThing) {
                        textView.setText(getString(R.string.got_data, fakeThing.id, fakeThing.name));
                    }
                },new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(FlatMapActivity.this, R.string.loading_failed, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
