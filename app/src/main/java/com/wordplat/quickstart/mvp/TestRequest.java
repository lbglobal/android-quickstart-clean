package com.wordplat.quickstart.mvp;

import com.wordplat.quickstart.bean.response.StringResponse;

import org.xutils.http.RequestParams;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * <p>TestRequest</p>
 * <p>Date: 2017/4/20</p>
 *
 * @author afon
 */

public class TestRequest extends BaseRequest {

    public static Observable<StringResponse> getBaidu() {
        return Observable.create(new Observable.OnSubscribe<StringResponse>() {
            @Override
            public void call(Subscriber<? super StringResponse> subscriber) {
                requestServer(subscriber, new RequestParams("http://m.qq.com/"), StringResponse.class);
            }
        }).subscribeOn(Schedulers.io());
    }
}
