package com.zcz.dao.Role;

import com.zcz.entity.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 获取角色信息
 * @fileName: RoleDao
 * @author: ZCZ
 * @date 2023年03月02日 22:48
 */
public interface RoleDao {
    // 获取角色列表
    public List<Role> getRoleList (Connection connection)throws SQLException;
}
