package com.wordplat.quickstart.bean.response;

import android.util.Log;

import com.wordplat.quickstart.BuildConfig;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * JSON解析
 *
 * @author liutao
 */
public class StringResponseParser implements ResponseParser {

    public static final String TAG = "JsonResponseParser";

    @Override
    public void checkResponse(UriRequest request) throws Throwable {}

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "##d 服务器返回数据：" + result);
        }

        StringResponse response = new StringResponse();
        response.setData(result);

        return response;
    }
}