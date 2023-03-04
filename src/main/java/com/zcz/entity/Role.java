package com.zcz.entity;

import java.util.Date;

/**
 * @description: 用户角色
 * @fileName: Role
 * @author: ZCZ
 * @date 2023年02月26日 11:38
 * @remark: 众阳健康
 */
public class Role {
    /**
     * ID
     */
    private Integer id;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 创建者
     */
    private Integer createdBy;
    /**
     * 创建时间
     */
    private Date creationDate;
    /**
     * 修改者
     */
    private Integer modifiBy;
    /**
     * 修改时间
     */
    private Date modifyDate;

    public Role() {
    }
    public Role(Integer id, String roleCode, String roleName, Integer createdBy, Date creationDate, Integer modifiBy, Date modifyDate) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
        this.modifiBy = modifiBy;
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createdBy=" + createdBy +
                ", creationDate=" + creationDate +
                ", modifiBy=" + modifiBy +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public Integer getModifiBy() {
        return modifiBy;
    }

    public void setModifiBy(Integer modifiBy) {
        this.modifiBy = modifiBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
