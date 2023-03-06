package com.zcz.service.user.serviceImpl;

import com.zcz.dao.BaseDao;
import com.zcz.dao.user.UserDao;
import com.zcz.dao.user.UserDaoImpl;
import com.zcz.entity.User;
import com.zcz.service.user.UserService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: 用户业务层实现
 * @FileName: UserServiceImpl
 * @Author: ZCZ
 * @Date 2023年02月26日 17:50
 */
public class UserServiceImpl implements UserService {

    // 业务层都会调用Dao层，所以我们要引入Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    /**
     * @Description: 根据用户编码据获取用户
     * @Date: 2023/3/5
     * @Param: [usercode]
     * @return: com.zcz.entity.User
     **/
    @Override
    public User getUserByUserCode(String usercode) {
        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserByUserCode(connection, usercode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    /**
     * @Description: 用户登录校验 用户名、密码
     * @Date: 2023/3/5
     * @Param: [usercode, userPassword]
     * @return: com.zcz.entity.User
     **/
    @Override
    public User login(String usercode, String userPassword) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        // 通过业务层，调用具体的 数据库操作
        try {
            user = userDao.getLoginUser(connection, usercode, userPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }


    /**
     * @Description: 根据用户ID修改用户密码
     * @Date: 2023/3/5
     * @Param: [id, password]
     * @return: boolean
     **/
    @Override
    public boolean UpdatePwd(int id, String password) throws SQLException {
        Connection connection = null;
        int rows = 0;
        boolean flag = false;
        // 通过操作数据库通用类获取连接
        connection = BaseDao.getConnection();
        // 修改密码
        rows = userDao.updatePwd(connection, id, password);
        // 密码修改成功，返回true，否则返回false
        if (rows > 0) {
            flag = true;
        }
        // 释放资源
        BaseDao.closeResource(connection, null, null);
        return flag;
    }

    /**
     * @Description: 根据用户名或者角色 获取用户列表数量
     * @Date: 2023/3/5
     * @Param: [userRole, userName]
     * @return: int
     **/
    @Override
    public int getUserCount(int userRole, String userName) throws SQLException {
        Connection connection = null;
        // 业务层调用公共类BaseDao方法获取连接
        connection = BaseDao.getConnection();
        // 调用dao查询
        int count = userDao.getUserCount(connection, userRole, userName);
        BaseDao.closeResource(connection, null, null);
        return count;
    }

    /**
     * @Description: 分页实现获取用户列表
     * @Date: 2023/3/5
     * @Param: [userRole, userName, pageSize, currentPageNo]
     * @return: java.util.List<com.zcz.entity.User>
     **/
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


    /*
     * @Author: ZCZ
     * @Description:  根据用户Id删除用户
     * @Date: 2023/3/6
     * @Param: [userId]
     * @return: [int]
     **/
    @Override
    public boolean deluser(int userId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);// 开启JDBC事务管理
            int delRows = userDao.deluser(connection, userId);
            if (delRows > 0) {
                flag = true;
            }
            // 事务提交
            connection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("---删除失败，事务回滚，rollback----");
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        return flag;
    }

    /**
     * @Description: 添加用户
     * @Date: 2023/3/5
     * @Param: [userCode, userName, gender, birthday, phone, createdBy, creationDate]
     * @return: boolean
     **/
    @Override
    public boolean addUser(int userCode, String userName, String password, int gender, String birthday, String phone, String address, int userRole, int createdBy, String creationDate) {
        Connection connection = null;
        boolean flag = false;
        // 调用dao增加
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);// 开启JDBC事务管理
            int count = userDao.addUser(connection, userCode, userName, password, gender, birthday, phone, address, userRole, createdBy, creationDate);
            if (count > 0) {
                flag = true;
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
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    /**
     * @Description: 修改用户信息
     * @Date: 2023/3/6
     * @Param: [user]
     * @return: boolean
     **/
    @Override
    public boolean updateUser(User user) {
        // 修改成功标志
        boolean updateFlag = false;
        Connection connection = null;
        try {
            // 调用数据库操作公共类，获取连接
            connection = BaseDao.getConnection();
            // 调用dao  修改用户信息
            int updateRows = userDao.updateUser(connection, user);
            // 开启事务
            connection.setAutoCommit(false);
            if (updateRows > 0) {
                updateFlag = true;
            }
            // 提交事务
            connection.commit();
        } catch (SQLException throwables) {
            try {
                // 若数据修改异常，事务回滚
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return updateFlag;
    }

    /**
     * @Description: 测试获取用户数量
     * @Date: 2023/3/5
     * @Param: []
     * @return: void
     **/
    @Test
    public void test() throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        int userCount = userService.getUserCount(0, null);
        System.out.println(userCount);
    }


}
