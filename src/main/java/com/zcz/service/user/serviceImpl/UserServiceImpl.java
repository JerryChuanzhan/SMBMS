package com.zcz.service.user.serviceImpl;

import com.zcz.dao.BaseDao;
import com.zcz.dao.user.UserDao;
import com.zcz.dao.user.UserDaoImpl;
import com.zcz.entity.User;
import com.zcz.service.user.UserService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户业务层实现
 * @fileName: UserServiceImpl
 * @author: ZCZ
 * @date 2023年02月26日 17:50
 */
public class UserServiceImpl implements UserService {
    //业务层都会调用Dao层，所以我们要引入Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String usercode, String userPassword) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        //通过业务层，调用具体的 数据库操作
        try {
            user = userDao.getLoginUser(connection, usercode,userPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    //根据用户ID修改用户密码
    @Override
    public boolean UpdatePwd(int id, String password) throws SQLException {
        Connection connection = null;
        int rows = 0;
        boolean flag = false;
        //通过操作数据库通用类获取连接
        connection = BaseDao.getConnection();
        //修改密码
        rows = userDao.updatePwd(connection, id, password);
        //密码修改成功，返回true，否则返回false
        if (rows > 0) {
            flag = true;
        }
        //释放资源
        BaseDao.closeResource(connection, null, null);
        return flag;
    }

    //根据用户名或者角色 获取用户列表数量
    @Override
    public int getUserCount( int userRole, String userName) throws SQLException {
        Connection connection = null;
        //业务层调用公共类BaseDao方法获取连接
        connection = BaseDao.getConnection();
        //调用dao查询
        int count = userDao.getUserCount(connection, userRole, userName);
        BaseDao.closeResource(connection,null,null);
        return count;
    }

    @Override
    public List<User> getUserList(int userRole, String userName, int pageSize, int currentPageNo) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("querUserName ---->" + userName);
        System.out.println("querUserRole ---->" + userRole);
        System.out.println("currentPageNo ---->" + currentPageNo);
        System.out.println("pageSize ---->" + pageSize);
            try {
                connection = BaseDao.getConnection();
                userList = userDao.getUserList(connection, userRole, userName, pageSize, currentPageNo);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return userList;
    }

    //添加用户
    @Override
    public boolean addUser(int userRole, String userName, int gender, Date birthday, String phone, int createdBy, Date creationDate) {
        Connection connection =null;
        boolean flag = false;
        //调用dao增加
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            int count = userDao.addUser(connection, userRole, userName,gender,birthday,phone,createdBy,creationDate);
            if (count>0){
                flag=true;
            }
            connection.commit();
        } catch (SQLException throwables) {
            try {
                System.out.println("------rollback-------");
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
       return flag;
    }

    @Test
    public void test() throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        int userCount = userService.getUserCount(0, null);
        System.out.println(userCount);
    }
}
