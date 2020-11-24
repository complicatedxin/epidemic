package com.sauvignon.epidemicstatistics.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil
{
    public static String doGet(String urlStr)
    {
        HttpURLConnection connection=null;
        StringBuilder sb=new StringBuilder();

        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        try {
            URL url=new URL(urlStr);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            if(connection.getResponseCode()==200)
            {
                is=connection.getInputStream();
                isr=new InputStreamReader(is);
                br=new BufferedReader(isr);
                String line=null;
                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br!=null) br.close();
                if(isr!=null) isr.close();
                if(is!=null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(connection!=null) connection.disconnect();
        }
        return sb.toString();
    }
}
