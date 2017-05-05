package com.wordplat.quickstart.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.bean.response.StringResponse;
import com.wordplat.quickstart.mvp.TestRequest;

import org.xutils.view.annotation.ContentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestRequest.getBaidu()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StringResponse>() {
                    @Override
                    public void call(StringResponse stringResponse) {
                        Log.d(TAG, "##d call: 成功 -> " + stringResponse.getData());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "##d call: 失败");
                    }
                });
    }

    /**
     * 点击两次退出
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
