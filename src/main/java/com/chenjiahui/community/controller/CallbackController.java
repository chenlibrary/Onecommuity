package com.chenjiahui.community.controller;

import com.chenjiahui.community.Model.Question;
import com.chenjiahui.community.Model.User;
import com.chenjiahui.community.dto.AccessToken;
import com.chenjiahui.community.dto.GithubUser;
import com.chenjiahui.community.dto.QuestionUser;
import com.chenjiahui.community.mapper.UserMapper;
import com.chenjiahui.community.provider.GithubProvider;
import com.chenjiahui.community.service.QuestionUserService;
import com.chenjiahui.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class CallbackController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;


    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.url}")
    private String client_url;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response)
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
        System.out.println("access_token="+access_token);
        GithubUser githubUser=githubProvider.okHttpGet(access_token);
        System.out.println("user="+githubUser);
        System.out.println("username="+githubUser.getName());

        if(githubUser!=null&&githubUser.getName()!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setAccontId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.UserifNull(user);
            response.addCookie(new Cookie("token",token));
            //登陆成功
            return "redirect:/";
        }else{
            //登陆失败
            return "redirect:/";
        }

    }
}
