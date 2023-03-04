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
 * @description: 角色业务实现类
 * @fileName: RoleServiceImpl
 * @author: ZCZ
 * @date 2023年03月02日 22:51
 */
public class RoleServiceImpl implements RoleService {
    //引入Dao
    private RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        System.out.println(roleList);
        for (Role role : roleList) {
            System.out.println(role.getRoleName());

        }
    }
}
