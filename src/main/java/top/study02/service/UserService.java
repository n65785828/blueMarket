package top.study02.service;

import top.study02.pojo.PackageUsers;
import top.study02.pojo.User;

import java.util.List;

public interface UserService {
    User findUserByUserNameAndPassowrd(User user);

    List<User> findAllUser();

    int addUser(User user);

    int deleteUserByPrimaryKey(Integer id);

    User findUserByPrimaryKey(Integer id);

    int updateUserById(User user);

    PackageUsers findUsersByPageNumAndSize(int pageNum, int pageSize);

    User findUserByUserName(String userName);

    User FindUserByUserID(String userId);

    User FindUserByUserName(String userName);

    int updateUserPasswordByPrimaryKey(User user);
}
