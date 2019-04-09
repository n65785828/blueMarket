package top.study02.service.serviceImp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.study02.mapper.UserMapper;
import top.study02.pojo.PackageUsers;
import top.study02.pojo.User;
import top.study02.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUserNameAndPassowrd(User user) {
        user = userMapper.findUserByPasswordAndUserName(user);
        if (user != null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            setUserAgeByBirthDate(users);
        }
        return user;
    }

    private void setUserAgeByBirthDate(List<User> users) {
        for (User user : users
        ) {
            Date birthday = user.getBirthday();
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy");
            String birth = df.format(birthday);
            String now = df.format(date);
            int age = Integer.parseInt(now) - Integer.parseInt(birth) + 1;
            user.setAge(age);
        }
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userMapper.findAllUser();
        setUserAgeByBirthDate(users);

        return users;
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteUserByPrimaryKey(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public User findUserByPrimaryKey(Integer id) {
        return userMapper.findUserByPrimaryKey(id);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserByPrimaryKey(user);
    }

    @Override
    public PackageUsers findUsersByPageNumAndSize(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findAllUser();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        setUserAgeByBirthDate(users);
        PackageUsers packageUsers = new PackageUsers();
        packageUsers.setUsers(users);
        packageUsers.setTotalPageNum(pageInfo.getPages());
        return packageUsers;
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userMapper.findUserByUserName(userName);
        List<User> users = new ArrayList<>();
        if(user!=null){
            users.add(user);
            setUserAgeByBirthDate(users);
        }
        return user;
    }

    @Override
    public User FindUserByUserID(String userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public User FindUserByUserName(String userName) {
        return userMapper.FindUserByUserName(userName);
    }

    @Override
    public int updateUserPasswordByPrimaryKey(User user) {
        return userMapper.updateUserPasswordByPrimaryKey(user);
    }


}
