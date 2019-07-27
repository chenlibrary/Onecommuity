package com.chenjiahui.community.provider;

import com.alibaba.fastjson.JSON;
import com.chenjiahui.community.dto.AccessToken;
import com.chenjiahui.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String okHttpPost(AccessToken accessToken){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            System.out.println("string"+string);
            String stringsplit=string.split("&")[0].split("=")[1];
            System.out.println("stringsplit:"+stringsplit);
            return stringsplit;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public GithubUser okHttpGet(String access_token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
