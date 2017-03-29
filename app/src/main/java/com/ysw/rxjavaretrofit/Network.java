package com.ysw.rxjavaretrofit;

import com.ysw.rxjavaretrofit.flatmap.FakeApi;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Swy on 2017/3/28.
 */

public class Network {
    private static final String baseMeiziUrl ="http://gank.io/api/";

    private static MeiziApi meiziApi;
    private static FakeApi fakeApi;

    private static Converter.Factory gsonConverterFactory= GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static MeiziApi getMeiziEntity(){
        if (meiziApi==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(baseMeiziUrl)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            meiziApi=retrofit.create(MeiziApi.class);
        }
        return meiziApi;
    }

    public static FakeApi getFakeApi() {
        if (fakeApi == null) {
            fakeApi = new FakeApi();
        }
        return fakeApi;
    }

}
