package com.sauvignon.epidemicstatistics.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpClientUtil
{
    public static String doGet(String urlStr)
    {
        String result=null;
        CloseableHttpClient client=null;
        CloseableHttpResponse response=null;
        try {
            client= HttpClients.createDefault();
            HttpGet get=new HttpGet(urlStr);
            get.addHeader("Accept", "application/json");
            get.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(5000)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .build());
            response=client.execute(get);
            HttpEntity entity=response.getEntity();
            result= EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null) response.close();
                if(client!=null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
