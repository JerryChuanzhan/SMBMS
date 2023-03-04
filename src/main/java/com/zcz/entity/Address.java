package com.zcz.entity;

import java.util.Date;

/**
 * @description: 地址信息
 * @fileName: Address
 * @author: ZCZ
 * @date 2023年02月26日 11:37
 * @remark: 众阳健康
 */
public class Address {
    /**
     * ID
     */
    private Integer id;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 地址描述
     */
    private String addressDesc;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 电话
     */
    private String tel;
    /**
     * 创建者
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 更新者
     */
    private String modifyBy;
    /**
     * 更新时间
     */
    private Date modifyDate;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", addressDesc='" + addressDesc + '\'' +
                ", postCode='" + postCode + '\'' +
                ", tel='" + tel + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationDate=" + creationDate +
                ", modifyBy='" + modifyBy + '\'' +
                ", modifyDate=" + modifyDate +
                ", userName='" + userName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Address(Integer id, String contact, String addressDesc, String postCode, String tel, String createdBy, Date creationDate, String modifyBy, Date modifyDate, String userName) {
        this.id = id;
        this.contact = contact;
        this.addressDesc = addressDesc;
        this.postCode = postCode;
        this.tel = tel;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyBy = modifyBy;
        this.modifyDate = modifyDate;
        this.userName = userName;
    }

    public Address() {
    }

    /*
     * 用户名称
     */
    private String userName;
}
