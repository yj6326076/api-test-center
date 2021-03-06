/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.runner;

import com.little.smile.builder.AptHttpClientBuilder;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.io.CloseMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.Future;

/**
 * AptAsyncHttpRunner
 *
 * @author Little Smile Boy
 * @since 2021-12-06
 */
public class AptAsyncHttpRunner {
    private final List<CloseableHttpAsyncClient> clientList;
    private final HttpClientContext context;

    /**
     * 创建需要cookie保持的异步请求执行
     */
    public AptAsyncHttpRunner() {
        this(true);
    }

    /**
     * 创建异步请求执行类
     * @param needCookie 是否需要cookie保持
     */
    public AptAsyncHttpRunner(boolean needCookie) {
        clientList = Collections.synchronizedList(new ArrayList<>());
        if (needCookie) {
            context = AptHttpClientBuilder.buildContext();
        } else {
            context = null;
        }
    }

    /**
     * 执行http请求
     * @param request http请求内容
     * @param futureCallback 回调函数
     * @return 执行结果
     */
    @SneakyThrows
    public Future<SimpleHttpResponse> execute(SimpleHttpRequest request, FutureCallback<SimpleHttpResponse> futureCallback) {
        CloseableHttpAsyncClient client = AptHttpClientBuilder.buildAsync();
        clientList.add(client);
        client.start();
        return client.execute(request, context, futureCallback);
    }

    public void closeAll() {
        for (CloseableHttpAsyncClient client:clientList) {
            client.close(CloseMode.GRACEFUL);
        }
    }
}
