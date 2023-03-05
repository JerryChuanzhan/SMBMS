package com.zcz.dao.Role;

import com.zcz.entity.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: 获取角色信息
 * @FileName: RoleDao
 * @Author: ZCZ
 * @Date 2023年03月02日 22:48
 */
public interface RoleDao {
    /**
     * @Description: 取角色列表
     * @Date: 2023/3/5
     * @Param: [connection]
     * @return: java.util.List<com.zcz.entity.Role>
     **/
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
