package com.ysw.rxjavaretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ysw.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Swy on 2017/3/28.
 */

public class MainActicity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_retrofir_main);
        textView= (TextView) findViewById(R.id.tv_response);
    }

    public void retrofitOnClick(View view) {
        Network.getMeiziEntity().getMeiziOnlyRetrofit(1,1).enqueue(new Callback<MeiziEntity>() {
            @Override
            public void onResponse(Call<MeiziEntity> call, Response<MeiziEntity> response) {
                textView.setText(response.body().toString());
                Toast.makeText(MainActicity.this,response.body().getResults().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MeiziEntity> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void retrofitOnClickRxjava(View view) {
        Network.getMeiziEntity().getMeizi(1,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MeiziEntity>() {
                    //onCompleted onError只有一个会触发
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MeiziEntity meiziEntity) {
                        textView.setText(meiziEntity.toString());
                        Toast.makeText(MainActicity.this,meiziEntity.getResults().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
