package com.wordplat.quickstart.bean.response;

import org.xutils.http.annotation.HttpResponse;

/**
 * <p>StringResponse</p>
 * <p>Date: 2017/4/20</p>
 *
 * @author afon
 */

@HttpResponse(parser = StringResponseParser.class)
public class StringResponse extends ServerResponse {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
