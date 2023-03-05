package com.zcz.service.user;

import com.zcz.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description: 用户业务层
 * @FileName: UserService
 * @Author: ZCZ
 * @Date 2023年02月26日 17:48
 */
public interface UserService {
    /**
     * @Description: 用户登录
     * @Date: 2023/3/5
     * @Param: [usercode, userPassword]
     * @return: com.zcz.entity.User
     **/
    public User login(String usercode, String userPassword) throws SQLException;

    /**
     * @Description: 根据用户编码据获取用户
     * @Date: 2023/3/5
     * @Param: [usercode]
     * @return: com.zcz.entity.User
     **/
    public User getUserByUserCode(String usercode);

    /**
     * @Description: 根据用户ID修改密码
     * @Date: 2023/3/5
     * @Param: [id, password]
     * @return: boolean
     **/
    public boolean UpdatePwd(int id, String password) throws SQLException;

    /**
     * @Description: 根据用户名或者角色 获取用户列表数量
     * @Date: 2023/3/5
     * @Param: [userRole, userName]
     * @return: int
     **/
    public int getUserCount(int userRole, String userName) throws SQLException;

    /**
     * @Description: 获取用户列表
     * @Date: 2023/3/5
     * @Param: [userRole, userName, pageSize, currentPageNo]
     * @return: java.util.List<com.zcz.entity.User>
     **/
    public List<User> getUserList(int userRole, String userName, int pageSize, int currentPageNo);

    /**
     * @Description: 添加用户
     * @Date: 2023/3/5
     * @Param: [userCode, userName, gender, birthday, phone, createdBy, creationDate]
     * @return: boolean
     **/
    public boolean addUser(int userCode, String userName, int gender, String birthday, String phone, int createdBy, String creationDate);


}
