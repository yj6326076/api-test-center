/*
 * this is little boy's personal project,right received by yj6326076@hotmail.com from 2021 to 2021
 */

package com.little.smile;

import com.little.smile.runner.AptAsyncHttpRunner;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequests;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * SingleTest
 *
 * @author Little Smile Boy
 * @since 2021-12-06
 */
public class SingleTest {
    @Test
    public void testCode() {
        AptAsyncHttpRunner aptAsyncHttpRunner = new AptAsyncHttpRunner();
        SimpleHttpRequest post = SimpleHttpRequests.post("https://httpbin.org/post");
        List<String> stringList = new ArrayList<>();
        for (int i = 0;i<20;i++ ) {
            aptAsyncHttpRunner.excute(post, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse result) {
                    stringList.add(result.getBodyText());
                    System.out.println(result.getBody());
                }

                @Override
                public void failed(Exception ex) {
                    System.out.println(ex);
                }

                @Override
                public void cancelled() {
                    System.out.println("cancelled");
                }
            });
        }
        aptAsyncHttpRunner.closeAll();
        System.out.println(stringList);
    }
}
