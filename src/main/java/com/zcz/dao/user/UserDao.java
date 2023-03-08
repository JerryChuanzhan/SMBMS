package com.zcz.dao.user;

import com.zcz.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * @Description: 用户dao
 * @FileName: UserDao
 * @Author: ZCZ
 * @Date 2023年02月26日 17:07
 */
public interface UserDao {
    /**
     * @return : com.zcz.entity.User
     * @Description :  得到要登录的用户
     * @Date : 18:28 2023/3/5
     * @Param : [connection, usercode, userPassword]
     **/
    public User getLoginUser(Connection connection, String usercode, String userPassword) throws SQLException;

    /**
     * @return : com.zcz.entity.User
     * @Description :  根据用户编码据获取用户
     * @Date : 18:28 2023/3/5
     * @Param : [connection, usercode]
     **/
    public User getUserByUserCode(Connection connection, String usercode) throws SQLException;

    /**
     * @Description: 修改当前用户的密码
     * @Date: 2023/3/5
     * @Param: [connection, id, password]
     * @return: int
     **/
    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    /**
     * @Description: 根据用户名或者角色 获取用户列表数量
     * @Date: 2023/3/5
     * @Param: [connection, userRole, userName]
     * @return: int
     **/
    public int getUserCount(Connection connection, int userRole, String userName) throws SQLException;

    /**
     * @Description: 获取用户列表
     * @Date: 2023/3/5
     * @Param: [connection, userRole, userName, pageSize, currentPageNo]
     * @return: java.util.List<com.zcz.entity.User>
     **/
    public List<User> getUserList(Connection connection, int userRole, String userName, int pageSize, int currentPageNo) throws SQLException;

    /**
     * @Description: 添加用户
     * @Date: 2023/3/5
     * @Param: [connection, userCode, userName, gender, birthday, phone, createdBy, creationDate]
     * @return: int
     **/
    public int addUser(Connection connection, String userCode, String userName, String password, int gender, String birthday, String phone, String address, int userRole, int createdBy, String creationDate) throws SQLException;

    /**
     * @Author: ZCZ
     * @Description: 根据用户ID删除用户
     * @Date: 2023/3/6
     * @Param: [connection, userId]
     * @return: [java.sql.Connection, int]
     **/
    public int deluser(Connection connection, int userId);

    /**
     * @Description: 修改用户信息
     * @Date: 2023/3/6
     * @Param: [connection, user]
     * @return: int
     **/
    public int updateUser(Connection connection, User user);

    /*
     * @Author: ZCZ
     * @Description: 根据用户ID 获取用户信息
     * @Date: 2023/3/8
     * @Param: [connection, uid]
     * @return: [java.sql.Connection, int]
     **/
    public User getUserById(Connection connection,int uid);
}