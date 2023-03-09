package com.zcz.service.Bill;

import com.zcz.dao.BaseDao;
import com.zcz.dao.bill.BillDao;
import com.zcz.dao.bill.BillDaoImpl;
import com.zcz.entity.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: BillServiceImpl
 * @Description: 订单业务实现类
 * @Author: ZCZ
 * @Date: 2023/3/8 18:48
 */
public class BillServiceImpl implements BillService {

    // 业务层引入Dao层
    private BillDao billDao;

    public BillServiceImpl() {
        billDao = new BillDaoImpl();
    }

    /**
     * @Author: ZCZ
     * @Description: 查询供应商下对应的订单数量
     * @Date: 2023/3/8
     * @Param: [providerId]
     * @return: [java.lang.String]
     **/
    @Override
    public int getConut(String providerId) {
        PreparedStatement preparedStatement = null;
        int getRows = 0;
        Connection connection = BaseDao.getConnection();
        getRows = billDao.getConut(connection, providerId);
        BaseDao.closeResource(connection, preparedStatement, null);
        return getRows;
    }


    /**
     * @Author: ZCZ
     * @Description: 获取订单列表
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    @Override
    public List<Bill> getBillList(Bill bill) {
        PreparedStatement preparedStatement = null;
        List<Bill> billLists = new ArrayList<>();
        Connection connection = BaseDao.getConnection();
        billLists = billDao.getBillList(connection, bill);
        BaseDao.closeResource(connection, preparedStatement, null);
        return billLists;
    }

    /**
     * @Author: ZCZ
     * @Description: 添加新订单信息
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    @Override
    public boolean addBill(Bill bill) {
        PreparedStatement preparedStatement = null;
        int addRows = 0;
        boolean addFlag = false;
        Connection connection = BaseDao.getConnection();
        try {
            // 开启事务
            connection.setAutoCommit(false);
            addRows = billDao.addBill(connection, bill);
            if (addRows > 0) {
                addFlag = true;
            }
            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                // 事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return addFlag;
    }

    /**
     * @Author: ZCZ
     * @Description: 删除订单
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    @Override
    public int delBill(Bill bill) {
        PreparedStatement preparedStatement = null;
        int delBill = 0;
        Connection connection = BaseDao.getConnection();
        try {
            // 开启事务
            connection.setAutoCommit(false);
            delBill = billDao.delBill(connection, bill);
            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, preparedStatement, null);
        }
        return delBill;
    }

    /**
     * @Author: ZCZ
     * @Description: 通过Id查找对应订单信息
     * @Date: 2023/3/9
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    @Override
    public Bill getBillById(String id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Bill bill = null;
        Connection connection = BaseDao.getConnection();
        try {
            // 开启事务
            connection.setAutoCommit(false);
            bill = billDao.getBillById(connection, id);
            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                // 事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(connection, preparedStatement, null);
        }
        return bill;
    }

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    @Override
    public boolean updateBill(Bill bill) {
        PreparedStatement preparedStatement = null;
        boolean updateFlag = false;
        Connection connection = BaseDao.getConnection();
        try {
            // 开启事务
            connection.setAutoCommit(false);
            int i = billDao.updateBill(connection, bill);
            if (i > 0) {
                updateFlag = true;
            }
            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                // 事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            BaseDao.closeResource(connection, preparedStatement, null);
        }

        return updateFlag;
    }
}
