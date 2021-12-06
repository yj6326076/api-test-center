/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.runner;

import com.little.smile.builder.AptHttpClientBuilder;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.io.CloseMode;

import java.io.IOException;

/**
 * AptHttpRunner
 *
 * @author Little Smile Boy
 * @since 2021-12-03
 */
public class AptHttpRunner {
    private final CloseableHttpClient client;
    private final HttpClientContext context;

    /**
     * 创建默认的httpclient
     */
    public AptHttpRunner() {
        client = AptHttpClientBuilder.build();
        context = AptHttpClientBuilder.buildContext();
    }

    /**
     * 执行http请求
     * @param request http请求
     * @return 执行结果
     * @throws IOException 运行时异常
     */
    public CloseableHttpResponse execute(ClassicHttpRequest request) throws IOException {
        return client.execute(request,context);
    }

    /**
     * 异步执行http请求
     * @param request http请求
     * @return 执行结果
     * @throws IOException 运行时异常
     */
    public Disposable executeAsync(ClassicHttpRequest request,  Consumer<? super CloseableHttpResponse> consumer) throws IOException {
        return Flowable.fromCallable(() -> client.execute(request, context)).subscribeOn(Schedulers.io()).subscribe(consumer);
    }

    /**
     * 关闭httpclient
     */
    public void close() {
        client.close(CloseMode.GRACEFUL);
    }
}
