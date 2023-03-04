package com.zcz.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @description: 操作数据库公共类
 * @fileName: BaseDao
 * @author: ZCZ
 * @date 2023年02月26日 12:52
 */
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块，类加载的时候就初始化
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        // classLoader主要对类的请求提供服务，
        // 当JVM需要某类时，它根据名称向ClassLoader要求这个类，然后由ClassLoader返回这个类的class对象。
        // ClassLoader负责载入系统的所有资源（Class，文件，图片，来自网络的字节流等），
        //通过ClassLoader从而将资源载入JVM 中。每个class都有一个引用，指向自己的ClassLoader。

        //得到这个class类的反射，调用loader,把资源构造成 流
        InputStream resourceAsStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //编写'查询'公共类
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params, ResultSet resultSet) throws SQLException {
        //预编译SQL,在后面的preparedStatement直接执行
        preparedStatement = connection.prepareStatement(sql);
        //赋值SQL参数
        for (int i = 0; i < params.length; i++) {
            //setObject，占位符从1开始，数组下标从0开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        //执行SQL
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写'增、删、改'工具类
    public static int execute(Connection connection, PreparedStatement preparedStatement , String sql, Object[] params) throws SQLException {
        //预编译SQL,在后面的preparedStatement直接执行
        preparedStatement = connection.prepareStatement(sql);
        //赋值SQL参数
        for (int i = 0; i < params.length; i++) {
            //setObject，占位符从1开始，数组下标从0开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        //执行SQL
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    //释放资源
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;
        //ResultSet 释放资源
        if (resultSet != null) {
            try {
                resultSet.close();
                //GC回收（垃圾回收）
                resultSet = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }

        //connection 释放资源
        if (connection != null) {
            try {
                connection.close();
                //GC回收（垃圾回收）
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        //preparedStatement 释放资源
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                //GC回收（垃圾回收）
                preparedStatement = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return false;
    }
}
