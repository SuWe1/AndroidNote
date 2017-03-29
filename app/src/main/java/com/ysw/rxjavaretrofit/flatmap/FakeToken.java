// (c)2016 Flipboard Inc, All Rights Reserved.

package com.ysw.rxjavaretrofit.flatmap;

public class FakeToken {
    public String token;
    public boolean expired;

    public FakeToken() {
    }

    public FakeToken(boolean expired) {
        this.expired = expired;
    }
}
