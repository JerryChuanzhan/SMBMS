package com.zcz.service.user;

import com.zcz.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @description: 用户业务层
 * @fileName: UserService
 * @author: ZCZ
 * @date 2023年02月26日 17:48
 */
public interface UserService {
    //用户登录
    public User login(String usercode, String userPassword) throws SQLException;
    //根据用户ID修改密码
    public boolean UpdatePwd(int id, String password) throws SQLException;
    //根据用户名或者角色 获取用户列表数量
    public int getUserCount( int userRole, String userName)throws SQLException;
    // 获取用户列表
    public List<User> getUserList( int userRole, String userName, int pageSize, int currentPageNo);

    // 添加用户  String address ,
    public boolean addUser( int userRole, String userName, int gender , Date birthday , String phone, int createdBy , Date creationDate);



}
