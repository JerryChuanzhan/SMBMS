package com.zcz.dao.Role;

import com.zcz.dao.BaseDao;
import com.zcz.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 角色信息Dao实现类
 * @fileName: RoleDaoImpl
 * @author: ZCZ
 * @date 2023年03月02日 22:49
 */
public class RoleDaoImpl implements RoleDao{
    // 获取角色列表
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        ArrayList<Role> roleArrayList = new ArrayList<>();
        if (connection!=null){
            String sql = "select *  from smbms_role";
            Object[] params ={};
            resultSet = BaseDao.execute(connection, preparedStatement, sql, params, resultSet);
            while (resultSet.next()){
                Role role = new Role();
                role.setId( resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));
                roleArrayList.add(role);
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return roleArrayList;
    }
}
