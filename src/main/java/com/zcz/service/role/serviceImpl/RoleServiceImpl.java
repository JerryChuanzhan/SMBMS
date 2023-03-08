package com.zcz.service.role.serviceImpl;

import com.zcz.dao.BaseDao;
import com.zcz.dao.Role.RoleDao;
import com.zcz.dao.Role.RoleDaoImpl;
import com.zcz.entity.Role;
import com.zcz.service.role.RoleService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: 角色业务实现类
 * @FileName: RoleServiceImpl
 * @Author: ZCZ
 * @Date 2023年03月02日 22:51
 */
public class RoleServiceImpl implements RoleService {
    // 引入Dao
    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    /**
     * @Description: 获取用户列表
     * @Date: 2023/3/5
     * @Param: []
     * @return: java.util.List<com.zcz.entity.Role>
     **/
    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }

    @Test
    public void test() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        System.out.println(roleList);
        for (Role role : roleList) {
            System.out.println(role.getRoleName());

        }
    }
}
