package com.myuan.user.controller;

import com.google.code.kaptcha.Constants;
import com.google.common.base.Objects;
import com.myuan.user.entity.MyResult;
import com.myuan.user.entity.MyUser;
import com.myuan.user.service.UserService;
import com.myuan.user.utils.SaltPasswordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuwei
 * @date 2018/1/28 13:04
 * user接口层
 */
@Api("用户接口层")
@RestController
@RequestMapping("api")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("/user/{id}")
    public MyUser getUser(@PathVariable("id") Long id) {
        MyUser user = userService.getUserById(id);
        if (user != null) {
            user.setPassword("");
        }
        return user;
    }
    @ApiOperation(value = "name获取用户信息", notes = "name获取用户信息")
    @GetMapping("/user/name")
    public MyUser getUserByName(@RequestParam(value = "name") String name) {
        return userService.getUserByName(name);
    }
    @ApiOperation(value = "email获取用户信息", notes = "email获取用户信息")
    @GetMapping("/user/email")
    public MyUser getUserByEmail(@RequestParam(value = "email") String email) {
        return userService.getUserByEmail(email);
    }

    @ApiOperation(value = "密码修改", notes = "密码修改")
    @PutMapping("/user/{id}/pass")
    public MyResult updatePass(@PathVariable("id") Long id, String oldPass, String pass, String repass) {
        if (!Objects.equal(pass, repass)) {
            return MyResult.error("两次输入的密码不一致");
        }
        MyUser user = userService.getUserById(id);
        if (!user.getPassword().equals(SaltPasswordUtil.getNewPass(oldPass))) {
            return MyResult.error("原密码不正确");
        }
        MyResult result = userService.updateUserPass(id, pass);
        return result;
    }

    @ApiOperation(value = "用户信息修改", notes = "用户信息修改")
    @PutMapping("/user/{id}")
    public MyResult updateInfo(@PathVariable("id") Long id, String name, String sex, String province, String city, String description) {

        MyResult result = userService.updateUserInfo(id, name, sex, province, city, description);
        return result;
    }
//    @ApiOperation(value = "重置密码", notes = "重置密码")
//    @PostMapping("/user/passreset")
//    public MyResult resetPass(String email, String vercode, HttpServletRequest request) {
//        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if(!Objects.equal(code, vercode)) {
//            return MyResult.error("验证码错误");
//        }
//        MyResult result = userService.resetPass(email);
//        return result;
//    }
    @ApiOperation(value = "增加飞吻", notes = "增加飞吻")
    @PutMapping("/user/{id}/kiss")
    public MyResult addKiss(@PathVariable("id") Long id,@RequestParam(value = "kiss") Integer kiss) {
        MyResult result = userService.addUserKiss(id, kiss);
        return result;
    }
    /**
     * 注册 <liuwei> [2018/1/20 9:33]
     */
    @PostMapping("user/reg")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public MyResult register(@Valid MyUser user, BindingResult bindingResult, String repassword, String vercode, HttpServletRequest request) {
//        String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//        if(!Objects.equal(code, vercode)) {
//            return MyResult.error("验证码错误");
//        }
        if (bindingResult.hasErrors()) {
            return validForm(bindingResult);
        }
        if (!user.getPassword().equals(repassword)) {
            return MyResult.error("两次输入的密码不一致");
        }
        user.setPassword(SaltPasswordUtil.getNewPass(user.getPassword()));
        MyResult result = userService.saveUser(user);
        return result;
    }

}
