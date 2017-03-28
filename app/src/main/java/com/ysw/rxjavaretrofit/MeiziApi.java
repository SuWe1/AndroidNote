package com.ysw.rxjavaretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Swy on 2017/3/28.
 */

public interface MeiziApi {
    @GET("data/福利/{count}/{page}")
    Call<MeiziEntity> getMeiziOnlyRetrofit(@Path("count")int count, @Path("page") int page);

    @GET("data/福利/{count}/{page}")
    Observable<MeiziEntity> getMeizi(@Path("count")int count,@Path("page") int page);

}
