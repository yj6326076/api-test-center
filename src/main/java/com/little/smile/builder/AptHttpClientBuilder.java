/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile.builder;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.auth.CredentialsProvider;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

/**
 * AptHttpClientBuilder
 *
 * @author Little Smile Boy
 * @since 2021-12-02
 */
public class AptHttpClientBuilder {
    private static final Timeout DEFAULT_CONNECT_TIME_OUT = Timeout.ofSeconds(5);
    private static final Timeout DEFAULT_RESPONSE_TIME_OUT = Timeout.ofSeconds(60);

    public static CloseableHttpClient build() {
        return build(DEFAULT_CONNECT_TIME_OUT,DEFAULT_RESPONSE_TIME_OUT);
    }

    @SneakyThrows
    public static CloseableHttpClient build(Timeout connectTimeOut, Timeout responseTimeOut) {
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(SSLContexts.custom().loadTrustMaterial(null,(chain, authType) -> true).build())
                        .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                        .build())
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofSeconds(5))
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setConnectionTimeToLive(TimeValue.ofMinutes(1L))
                .build();
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(connectTimeOut)
                        .setResponseTimeout(responseTimeOut)
                        .setCookieSpec(StandardCookieSpec.STRICT)
                        .build())
                .build();
    }

    public static HttpClientContext buildContext() {
        return buildContext(DEFAULT_CONNECT_TIME_OUT,DEFAULT_RESPONSE_TIME_OUT);
    }

    public static HttpClientContext buildContext(Timeout connectTimeOut, Timeout responseTimeOut) {
        CookieStore cookieStore = new BasicCookieStore();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        HttpClientContext clientContext = HttpClientContext.create();
        clientContext.setCookieStore(cookieStore);
        clientContext.setCredentialsProvider(credentialsProvider);
        clientContext.setRequestConfig(RequestConfig.custom()
                .setConnectTimeout(connectTimeOut)
                .setResponseTimeout(responseTimeOut)
                .build());
        return clientContext;
    }

    public static CloseableHttpAsyncClient buildAsync() {
        return buildAsync(DEFAULT_CONNECT_TIME_OUT,DEFAULT_RESPONSE_TIME_OUT);
    }

    @SneakyThrows
    public static CloseableHttpAsyncClient buildAsync(Timeout connectTimeOut, Timeout responseTimeOut) {
        PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .setTlsStrategy(ClientTlsStrategyBuilder.create()
                        .setSslContext(SSLContexts.custom().loadTrustMaterial(null,(chain, authType) -> true).build())
                        .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setConnectionTimeToLive(TimeValue.ofMinutes(1L))
                .build();
        return HttpAsyncClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(connectTimeOut)
                        .setResponseTimeout(responseTimeOut)
                        .setCookieSpec(StandardCookieSpec.STRICT)
                        .build())
                .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
                .build();
    }
}
