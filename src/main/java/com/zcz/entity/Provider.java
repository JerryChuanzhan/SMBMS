package com.zcz.entity;

import java.util.Date;

/**
 * @description: 供应商信息
 * @fileName: Provider
 * @author: ZCZ
 * @date 2023年02月26日 11:37
 */
public class Provider {
    /**
     * ID
     */
    private Integer id;
    /**
     * 供应商编码
     */
    private String proCode;
    /**
     * 供应商名称
     */
    private String proName;
    /**
     * 供应商联系人
     */
    private String proContact;
    /**
     * 供应商电话
     */
    private String proPhone;
    /**
     * 供应商地址
     */
    private String proAdress;
    /**
     * 供应商传真
     */
    private String proFax;
    /**
     * 创建者
     */
    private Integer createdBy;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 更新时间
     */
    private Date modifyDate;
    /**
     * 更新者
     */
    private Integer modifyBy;

    public Provider() {
    }

    public Provider(Integer id, String proCode, String proName, String proContact, String proPhone, String proAdress, String proFax, Integer createdBy, Date creationDate, Date modifyDate, Integer modifyBy) {
        this.id = id;
        this.proCode = proCode;
        this.proName = proName;
        this.proContact = proContact;
        this.proPhone = proPhone;
        this.proAdress = proAdress;
        this.proFax = proFax;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifyDate = modifyDate;
        this.modifyBy = modifyBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProContact() {
        return proContact;
    }

    public void setProContact(String proContact) {
        this.proContact = proContact;
    }

    public String getProPhone() {
        return proPhone;
    }

    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }

    public String getProAdress() {
        return proAdress;
    }

    public void setProAdress(String proAdress) {
        this.proAdress = proAdress;
    }

    public String getProFax() {
        return proFax;
    }

    public void setProFax(String proFax) {
        this.proFax = proFax;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proContact='" + proContact + '\'' +
                ", proPhone='" + proPhone + '\'' +
                ", proAdress='" + proAdress + '\'' +
                ", proFax='" + proFax + '\'' +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                ", modifyDate=" + modifyDate +
                ", modifyBy=" + modifyBy +
                '}';
    }
}
