package top.study02.pojo;

import java.util.List;

public class PackageBill {

    private List<Bill> bills;
    private Integer totalPageNum;

    public PackageBill() {
    }

    public PackageBill(List<Bill> bills, Integer totalPageNum) {
        this.bills = bills;
        this.totalPageNum = totalPageNum;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
