package com.zcz.dao.user;

import com.mysql.jdbc.StringUtils;
import com.zcz.dao.BaseDao;
import com.zcz.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @description: 用户实现类
 * @fileName: UserImpl
 * @author: ZCZ
 * @date 2023年02月26日 17:10
 */
public class UserDaoImpl implements UserDao {
    //得到要登录的用户
    @Override
    public User getLoginUser(Connection connection, String usercode,String userPassword) throws SQLException {
        //准备公共数据库方法连接参数
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        //判断连接是否成功，然后执行失去了
        if (connection != null) {
            //编写查询 SQL
            String sql = "select  * from smbms_user where userCode = ? and userPassword = ?";
            //设置查询变量参数
            Object[] params = {usercode,userPassword};
            //调用数据公共方法执行查询
            resultSet = BaseDao.execute(connection, preparedStatement, sql, params, resultSet);
            //遍历查询结果对象，将值赋给 User
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("usercode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                // user.setGender(resultSet.getInt("gender"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        //根据查询结果，返回登录对象
        return user;
    }

    //修改当前用户密码
    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRows = 0;
        //获取到连接，再进行修改执行
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password, id};
            executeRows = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return executeRows;
    }

    //根据用户名或者角色 获取用户列表数量【最难理解的SQL】
    @Override
    public int getUserCount(Connection connection, int userRole, String userName) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int counts = 0;
        //获取到连接，再进行修改执行
        if (connection != null) {
            //万能的ArrayList
            ArrayList<Object> pramasList = new ArrayList<>();//用来存放参数
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u ,smbms_role r where u.userRole =r.id");

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                pramasList.add("%" + userName + "%");//index:0
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                pramasList.add(userRole);//index：1
            }
            //list转化成数组
            Object[] params = pramasList.toArray();
            //输出完整SQL语句，方便调试
            System.out.println("UserImpl -- > getUserCount:" + sql.toString());
            //获取结果集
            resultSet = BaseDao.execute(connection, preparedStatement, sql.toString(), params, resultSet);
            //若查询结果有数据，返回数量
            if (resultSet.next()) {
                //根据列标签（查询列别名）获取参数。转int
                counts = resultSet.getInt("count");
            }
            //关闭资源
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return counts;
    }

    // 获取用户列表
    @Override
    public List<User> getUserList(Connection connection, int userRole, String userName, int pageSize, int currentPageNo) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<User> userList = new ArrayList<>();
        System.out.println("querUserName ---->" + userName);
        System.out.println("querUserRole ---->" + userRole);
        System.out.println("currentPageNo ---->" + currentPageNo);
        System.out.println("pageSize ---->" + pageSize);
        if (connection != null) {
            ArrayList<Object> list = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            sql.append("select *  from smbms_user u ,smbms_role r where u.userRole =r.id");
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and userRole = ?");
                list.add(userRole);
            }
            //在数据库中，分页使用  limit   startIndex,pageSize; 总数
            //当前页  ：  （当前页-1）*页面大小
            sql.append(" order by u.creationDate DESC limit ?,? ");
            System.out.println(sql);
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println(sql);
            resultSet = BaseDao.execute(connection, preparedStatement, sql.toString(), params, resultSet);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setUserRoleName(resultSet.getString("roleName"));
                user.setGender(resultSet.getInt("gender"));
                userList.add(user);
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return userList;
    }

    //添加用户信息
    @Override
    public int addUser(Connection connection, int userRole, String userName, int gender, Date birthday, String phone, int createdBy, Date creationDate) throws SQLException {
        PreparedStatement preparedStatement=null;
        int addRows =0;
        if (connection!=null){
            String sql = "inset into smbms_user (userRole,userName,gender,birthday,phone,createdBy,creationDate) values (?,? ? ,? ,? ,? ,? )";
            Object[] params = {userRole,userName,gender,birthday,phone,createdBy,creationDate};
            int execute = BaseDao.execute(connection, preparedStatement, sql, params);
            BaseDao.closeResource(connection,null,null);
        }
        return addRows;
    }
}
