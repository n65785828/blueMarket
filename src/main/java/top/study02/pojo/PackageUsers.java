package top.study02.pojo;

import java.io.Serializable;
import java.util.List;
//PackageUsers
public class PackageUsers implements Serializable {

    private List<User> users;
    private Integer totalPageNum;

    public PackageUsers() {
    }

    public PackageUsers(List<User> users, Integer totalPageNum) {
        this.users = users;
        this.totalPageNum = totalPageNum;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
