package com.wordplat.quickstart.mvp;

import com.wordplat.quickstart.bean.response.StringResponse;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * <p>TestPresenter</p>
 * <p>Date: 2017/6/6</p>
 *
 * @author afon
 */

public class TestPresenter extends BasePresenter<LoadingView> {

    private String data;

    public void testSSL(final int requestCode) {
        Subscription subscription = TestRequest.testSSL()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StringResponse>() {
                    @Override
                    public void call(StringResponse stringResponse) {
                        data = stringResponse.getData();

                        baseView.onSuccess(requestCode);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        handleError(requestCode, throwable);
                    }
                });

        addSubscription(subscription);
    }

    public String getData() {
        return data;
    }
}
