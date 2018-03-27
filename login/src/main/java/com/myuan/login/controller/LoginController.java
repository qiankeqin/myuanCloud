package com.myuan.login.controller;

import com.myuan.login.client.UserRemoteClient;
import com.myuan.login.entity.MyResult;
import com.myuan.login.service.LoginSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuwei
 * @date 2018/1/19 14:28
 *
 */
@Log4j
@Api("用户登录")
@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    private LoginSerivce loginSerivce;

    /**
     * 登录 <liuwei> [2018/1/19 16:04]
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public MyResult login(String email, String password, String vercode) {
//        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if(!Objects.equal(code, vercode)) {
//            return MyResult.error("验证码错误");
//        }
        log.info(email + " : 登陆成功");
        return loginSerivce.login(email, password);
    }

    @PostMapping("logout")
    @ApiOperation(value = "退出登录", notes = "退出登录")
    public MyResult logout(HttpServletResponse response) {
        response.addCookie(new Cookie("token", ""));
        return MyResult.ok("");
    }
}