package tn.esprit.controllers;


import okhttp3.*;

import java.io.IOException;

public class SmsSender {

    public static void sendSms(String phoneNumber, String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"destinations\":[{\"to\":\"" + "216" +phoneNumber + "\"}],\"from\":\"E.V.H\",\"text\":\"" + message + "\"}]}");
        Request request = new Request.Builder()
                .url("https://vvgeqe.api.infobip.com/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", "App 675aa50d15689054e8767da517907e38-ed11e571-4920-4985-a06c-6cb73a66c89a")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response code: " + response.code());
        System.out.println("Response body: " + response.body().string());
        System.out.println("sms sent");
    }

}