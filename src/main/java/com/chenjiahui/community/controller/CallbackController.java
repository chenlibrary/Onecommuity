package com.chenjiahui.community.controller;

import com.chenjiahui.community.dto.AccessToken;
import com.chenjiahui.community.dto.GithubUser;
import com.chenjiahui.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CallbackController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.url}")
    private String client_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request)
    {
        System.out.println("callback in");
        System.out.println("client_id"+client_id);
        System.out.println("client_secret"+client_secret);
        System.out.println("client_url"+client_url);
        AccessToken accessToken=new AccessToken();
        accessToken.setClient_id(client_id);
        accessToken.setClient_secret(client_secret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(client_url);
        accessToken.setState(state);
        String access_token=githubProvider.okHttpPost(accessToken);
        GithubUser user=githubProvider.okHttpGet(access_token);
        System.out.println("user="+user);
        System.out.println("username="+user.getName());

        if(user!=null){
            //登陆成功
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }

    }
}
