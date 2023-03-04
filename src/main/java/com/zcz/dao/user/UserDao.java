package com.zcz.dao.user;

import com.zcz.entity.Role;
import com.zcz.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 * @description: 用户dao
 * @fileName: UserDao
 * @author: ZCZ
 * @date 2023年02月26日 17:07
 */
public interface UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection connection, String usercode,String userPassword) throws SQLException;

    //修改当前用户的密码
    public int updatePwd(Connection connection,int id,String password)throws SQLException;

    //根据用户名或者角色 获取用户列表数量
    public int getUserCount(Connection connection,int userRole,String userName)throws SQLException;

    // 获取用户列表
    public List<User> getUserList(Connection connection, int userRole, String userName,int pageSize,int currentPageNo)throws SQLException;

    // 添加用户  String address ,
    public int addUser(Connection connection, int userRole, String userName, int gender , Date birthday ,String phone,int createdBy ,Date creationDate)throws SQLException;


}