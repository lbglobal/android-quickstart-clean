package com.wordplat.quickstart.mvp;

import com.wordplat.quickstart.bean.request.ServerRequestParams;
import com.wordplat.quickstart.bean.response.StringResponse;

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

    public static Observable<StringResponse> testSSL() {
        return Observable.create(new Observable.OnSubscribe<StringResponse>() {
            @Override
            public void call(Subscriber<? super StringResponse> subscriber) {
                ServerRequestParams requestParams = new ServerRequestParams("https://api.wordplat.com/ts/v1/testssl");
                requestParams.commit();

                requestServer(subscriber, requestParams, StringResponse.class);
            }
        }).subscribeOn(Schedulers.io());
    }
}
