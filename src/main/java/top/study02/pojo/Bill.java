package top.study02.pojo;

//create table bill(
//        id int primary key auto_increment,
//        goodsId varchar(80) not null unique,
//        goodsName varchar(50) not null unique,
//        provider int not null,
//        goodsPrice bigint not null,
//        payment boolean not null,
//        createDate date not null
//        )

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;

public class Bill {
    private int id;
    private String goodsId;
    private String goodsName;
    private Provider provider;//账单与供应商一对一
    private Long goodsPrice;
    private Boolean payment; //true 表示已付款 flase表示未付款
    private Date createDate;
    private Long goodsNum; //数量
    private String unit; //单位


    public Bill() {
    }

    public Bill(int id, String goodsId, String goodsName, Provider provider, Long goodsPrice, Boolean payment, Date createDate, Long goodsNum, String unit) {
        this.id = id;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.provider = provider;
        this.goodsPrice = goodsPrice;
        this.payment = payment;
        this.createDate = createDate;
        this.goodsNum = goodsNum;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Boolean getPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", provider=" + provider +
                ", goodsPrice=" + goodsPrice +
                ", payment=" + payment +
                ", createDate=" + createDate +
                ", goodsNum=" + goodsNum +
                ", unit='" + unit + '\'' +
                '}';
    }
}
