package com.zcz.service.role;

import com.zcz.entity.Role;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 角色业务层
 * @fileName: RoleService
 * @author: ZCZ
 * @date 2023年03月02日 22:50
 */
public interface RoleService {
    //获取角色列表
    public List<Role> getRoleList();
}
