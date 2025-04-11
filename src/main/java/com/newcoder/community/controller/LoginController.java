package com.newcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ClassName: LoginController
 * Package: com.newcoder.community.controller
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/11 16:32
 */
@Controller
public class LoginController {

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

}
