package top.study02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageForwardController {

    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }
    @RequestMapping("/password.html")
    public String password(){
        return "password";
    }
    @RequestMapping("/public.html")
    public String toPublic(){
        return "public";
    }





}
