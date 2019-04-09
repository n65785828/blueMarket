package top.study02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.study02.mapper.UserMapper;
import top.study02.pojo.PackageUsers;
import top.study02.pojo.User;
import top.study02.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 跳转到添加用户页面
     * @return
     */
    @RequestMapping("/userAdd.html")
    public String userAdd(){
        return "userAdd";
    }

    /**
     * 接收添加用户请求，进行添加操作
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("addUser")
    public String addUser(User user,HttpServletRequest request){
        int result = userService.addUser(user);
        System.out.println(result==1?"添加成功":"添加失败");
        return "redirect:/userList.html";
    }

    /**
     * 根据用户id删除用户
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String deleteUser(Integer id){
        int result = userService.deleteUserByPrimaryKey(id);
        System.out.println(result==1?"删除成功":"删除失败");
        return  "forward:/userList.html";
    }

    /**
     *根据ID查看用户信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/userView.html")
    public String userView(Integer id, Model model){
        User user = userService.findUserByPrimaryKey(id);
        model.addAttribute("user",user);
        return "userView.html";
    }

    /**
     * 根据用户ID返回修改用户信息页面
     * @return
     */
    @RequestMapping(value = "/userUpdate.html",method = RequestMethod.GET)
    public String forWardUserUpdate(Integer id, Model model,Integer pageNum){
        User user = userService.findUserByPrimaryKey(id);
        model.addAttribute("user",user);
        model.addAttribute("pageNum",pageNum);
        return "userUpdate";
    }

    /**
     * 根据id 更新用户数据
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/userUpdate.html",method = RequestMethod.POST)
    public String userUpdate(User user, Model model,int pageNum){
        int result = userService.updateUserById(user);
        System.out.println(result==1?"更新成功":"更新失败");
        return userList(model,8,pageNum);
    }

    /**
     * 分页查看用户
     * @param model
     * @return
     */
    @RequestMapping(value = "/userList.html",method = RequestMethod.GET)
    public String userList(Model model,@RequestParam(name = "pageSize",defaultValue = "8") int pageSize,@RequestParam(name ="pageNum",defaultValue = "1") int pageNum){
        PackageUsers packageUsers = userService.findUsersByPageNumAndSize(pageNum, pageSize);
        List<User> userList = packageUsers.getUsers();
        model.addAttribute("users",userList);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPageNum",packageUsers.getTotalPageNum());
        return "userList";
    }

    /**
     * 分页查看用户
     * @param pageNum 第几页
     * @param pageSize 每页数量
     * @return 返回pageSize数量的User集合
     */
    @RequestMapping(value = "/userList.html",method = RequestMethod.POST)
    @ResponseBody
    public List<User> userListByPageNum(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize){
        PackageUsers PackageUsers = userService.findUsersByPageNumAndSize(pageNum, pageSize);
        return PackageUsers.getUsers();
    }

    /**
     * 更具用户名称查找用户
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping("/queryUser")
    public String queryUser(String userName,Model model){
        model.addAttribute("pageNum",1);
        model.addAttribute("totalPageNum",1);
        if("".equals(userName)){
            model.addAttribute("noFound","请输入查询内容");
            return "userList";
        }
        User user =userService.findUserByUserName(userName);
        List<User> users = new ArrayList<>();
        if (user!=null){
            users.add(user);
        }else {
            model.addAttribute("noFound","没有查询到");
        }
        model.addAttribute("users",users);
        return "userList";
    }

    /**
     * 判断用户编号是否重复
     * @param userId
     * @return
     */
    @RequestMapping("/checkUserId")
    @ResponseBody
    public Map<String ,String> checkUserId(String userId){
        int flag =1;
        Map<String ,String> result = new HashMap<>();
        return checkUserUpdateValue(userId, result,flag);
    }

    /**
     * 判断用户名是否已存在
     * @param userName
     * @return
     */
    @RequestMapping("/checkUserName")
    @ResponseBody
    public Map<String ,String> checkUserName(String userName){
        int flag =2;//根据名字查询
        Map<String ,String> result = new HashMap<>();
        return checkUserUpdateValue(userName,result,flag);
    }


    /**
     * 判断重复编号和账号的公共方法
     * @param queryVal
     * @param result
     * @param flag
     * @return
     */
    private Map<String, String> checkUserUpdateValue(String queryVal, Map<String, String> result,int flag) {
        if("".equals(queryVal)){
            result.put("result","0");//1代表不存在该userId
            return result;
        }
        User user =null;
        if(flag==1){
            user = userService.FindUserByUserID(queryVal);
        }
        if(flag==2){
            user =userService.findUserByUserName(queryVal);
        }
        if(user!=null){
            result.put("result","0");//0代表数据库中已存在该userId
            return result;
        }
        result.put("result","1");//1代表不存在该userId
        return result;
    }

    @RequestMapping("/modifyPassword")
    public String modifyPassword(Map<String,String> result,String oldPassword , String newPassword, String repassword, HttpServletRequest request, HttpServletResponse response) throws Exception{
        User user = (User) request.getSession().getAttribute("user");
        if(!user.getUserPassword().equals(oldPassword)){
            result.put("error","原始密码不正确");
            return "password";
        }
        if(!repassword.equals(newPassword)){
            result.put("error1","密码不一致");
            return "password";
        }
        if(newPassword.length()<6){
            result.put("error1","密码长度不能小于6位");
            return "password";
        }
        user.setUserPassword(newPassword);
        userService.updateUserPasswordByPrimaryKey(user);
        request.getSession().removeAttribute("user");
        response.setContentType("text/html;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("密码修改成功，3秒后跳转至首页,请重新登录.......<br>".getBytes());
        outputStream.write("如未跳转请<a href='/login.html'>点击此处</a>".getBytes());
        response.setHeader("refresh","3;url=/login.html");
        return null;
    }




}
