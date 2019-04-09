package top.study02.pojo;

import java.io.Serializable;
import java.util.Date;
//     create table user(
//        id int primary key auto_increment,
//        userId varchar(60) not null unique,
//        userName varchar(60) not null unique,
//        userPassword varchar(60) not null,
//        sex enum('1','2') default '1',
//        birthday date ,
//        userphone varchar(30) ,
//        userAddress varchar(80),
//        userlv enum('1','2','3') default '3'
//        );

public class User implements Serializable {

    private Integer id;
    private String userId;
    private String userName;
    private String userPassword;
    private boolean sex;//true 代表男 false 代表女
    private Date birthday;
    private String userphone;
    private String userAddress;
    private String userlv;
    private int age;

    public User() {
    }

    public User(Integer id, String userId, String userName, String userPassword, boolean sex, Date birthday, String userphone, String userAddress, String userlv, int age) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.sex = sex;
        this.birthday = birthday;
        this.userphone = userphone;
        this.userAddress = userAddress;
        this.userlv = userlv;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserlv() {
        return userlv;
    }

    public void setUserlv(String userlv) {
        this.userlv = userlv;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", userphone='" + userphone + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userlv='" + userlv + '\'' +
                ", age=" + age +
                '}';
    }
}
