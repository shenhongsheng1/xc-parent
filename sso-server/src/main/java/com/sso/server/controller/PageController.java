package com.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ShenHongSheng
 * ClassName: PageController
 * Description:
 * Date: 2021/2/5 14:47
 * @version V1.0
 */
@Controller
public class PageController {

    @RequestMapping("/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }
}
