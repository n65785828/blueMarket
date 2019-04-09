package top.study02.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.study02.pojo.User;

import java.util.List;


public interface UserMapper {

    @Select("select * from user where userName=#{userName} and userPassword=#{userPassword}")
    User findUserByPasswordAndUserName(User user);
    @Select("select * from user")
    List<User> findAllUser();
    @Insert("insert into user values(null,#{userId},#{userName},#{userPassword},#{sex},#{birthday},#{userphone},#{userAddress},#{userlv});")
    int addUser(User user);
    @Delete("delete from user where id = #{param1}")
    int deleteUserById(Integer id);
    @Select("select * from user where id=#{param1}")
    User findUserByPrimaryKey(Integer id);
    @Update("update user set userName=#{userName} ,sex=#{sex} ,birthday=#{birthday},userphone=#{userphone},userAddress=#{userAddress},userlv=#{userlv} where id=#{id}")
    int updateUserByPrimaryKey(User user);
    @Select("select * from user where userName=#{param1} ")
    User findUserByUserName(String userName);
    @Select("select * from user where userId=#{param1}")
    User findUserByUserId(String userId);
    @Select("select * from user where userName=#{userName}")
    User FindUserByUserName(String userName);
    @Update("update user set userPassword=#{userPassword} where id=#{id}")
    int updateUserPasswordByPrimaryKey(User user);
}
