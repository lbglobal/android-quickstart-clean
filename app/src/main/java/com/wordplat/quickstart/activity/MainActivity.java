package com.wordplat.quickstart.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.mvp.LoadingViewListener;
import com.wordplat.quickstart.mvp.TestPresenter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @ViewInject(R.id.text) private TextView text = null;

    private final TestPresenter testPresenter = new TestPresenter();

    private static final int REQUEST_DATA = 524;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        testPresenter.attachView(viewListener);
        testPresenter.testSSL(REQUEST_DATA);
    }

    @Override
    protected void onPause() {
        super.onPause();

        testPresenter.detachView();
    }

    private final LoadingViewListener viewListener = new LoadingViewListener() {

        @Override
        public void onSuccess(int requestCode) {
            switch (requestCode) {
                case REQUEST_DATA:
                    text.setText(testPresenter.getData());
                    break;
            }
        }
    };

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
