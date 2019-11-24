package com.cobot.lib;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.cobot.utils.AppData;

@SuppressWarnings("deprecation")
public class APICalls {
    public static void Execute_postAPI(String postUrl, String inputJson) throws Exception {
        String[] headerNames = AppData.properties.getProperty("headerNames").split(";");
        String[] headerValues = AppData.properties.getProperty("headerValues").split(";");
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(postUrl);
            StringEntity params = new StringEntity(inputJson);
            for (int i = 0; i < headerNames.length; i++) {
                request.addHeader(headerNames[i], headerValues[i]);
            }
            request.setEntity(params);
            httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            httpClient.getConnectionManager().shutdown(); // Deprecated
        }
    }
}