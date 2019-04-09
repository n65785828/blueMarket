package top.study02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import top.study02.pojo.User;
import top.study02.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * 登录处理器，登录成功后跳转到public主页
     * @param user
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/check_login")
    public String checkLogin(User user, Model model,HttpServletRequest request){
        if(user==null||user.getUserName()==null||user.getUserPassword()==null){
            return "login";
        }
        if(user.getUserName().equals("")||user.getUserPassword().equals("")){
            model.addAttribute("error","用户名密码不能为空");
            return "login";
        }
        user = userService.findUserByUserNameAndPassowrd(user);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            return "index";
        }
        model.addAttribute("error","用户名密码不正确");
        return "login";
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request){
        if(request.getSession().getAttribute("user")!=null){
            request.getSession().removeAttribute("user");
        }
        return "redirect:login.html";
    }


}
