package top.study02.pojo;

import java.util.Date;

public class Provider {
    private int id ;
    private String proId;//
    private String providerName;//
    private String contacts;//
    private String contactPhone;//
    private String faxNum;//
    private Date createDate;
    private String describe;
    private String address;//

    public Provider() {
    }

    public Provider(int id, String proId, String providerName, String contacts, String contactPhone, String faxNum, Date createDate, String describe, String address) {
        this.id = id;
        this.proId = proId;
        this.providerName = providerName;
        this.contacts = contacts;
        this.contactPhone = contactPhone;
        this.faxNum = faxNum;
        this.createDate = createDate;
        this.describe = describe;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", proId='" + proId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", faxNum='" + faxNum + '\'' +
                ", createDate=" + createDate +
                ", describe='" + describe + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
