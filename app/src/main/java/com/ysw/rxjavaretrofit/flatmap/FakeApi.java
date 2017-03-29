package com.ysw.rxjavaretrofit.flatmap;

import java.util.Random;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Swy on 2017/3/28.
 * 出于安全性考虑 有些服务器要求传入token才还回数据 这就需要连续获取两步数据
 * 1.token 2.目标数据
 * 用flatmap可以用比较清晰的代码实现连续数据请求 避免了callback的嵌套
 */

public class FakeApi {
    Random random=new Random();
    //第一步 还回的是Observable<FakeToken>
    public Observable<FakeToken> getFakeToken(String fakeAuth){
        return Observable.just(fakeAuth)
                .map(new Func1<String, FakeToken>() {
                    @Override
                    public FakeToken call(String s) {
                        // Add some random delay to mock the network delay
                        int fakeNetworkTimeCost = random.nextInt(500) + 500;
                        try {
                            Thread.sleep(fakeNetworkTimeCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        FakeToken fakeToken = new FakeToken();
                        fakeToken.token = createToken();
                        return fakeToken;
                    }
                });
    }
    private static String createToken() {
        return "fake_token_" + System.currentTimeMillis() % 10000;
    }

    //第er步 还回的是Observable<FakeThing>
    public Observable<FakeThing> getFakeThing(FakeToken fakeToken){
        return Observable.just(fakeToken)
                .map(new Func1<FakeToken, FakeThing>() {
                    @Override
                    public FakeThing call(FakeToken fakeToken) {
                        // Add some random delay to mock the network delay
                        int fakeNetworkTimeCost = random.nextInt(500) + 500;
                        try {
                            Thread.sleep(fakeNetworkTimeCost);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (fakeToken.expired) {
                            throw new IllegalArgumentException("Token expired!");
                        }

                        FakeThing fakeData = new FakeThing();
                        fakeData.id = (int) (System.currentTimeMillis() % 1000);
                        fakeData.name = "FAKE_USER_" + fakeData.id;
                        return fakeData;
                    }
                });
    }
}
