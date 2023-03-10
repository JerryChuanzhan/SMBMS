package com.zcz.dao.bill;

import com.mysql.jdbc.StringUtils;
import com.zcz.dao.BaseDao;
import com.zcz.entity.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BillDaoImpl
 * @Description: 订单Dao层实现类
 * @Author: ZCZ
 * @Date: 2023/3/8 18:40
 */
public class BillDaoImpl implements BillDao {

    /**
     * @Author: ZCZ
     * @Description: 查询供应商下对应的订单数量
     * @Date: 2023/3/8
     * @Param: [connection, providerId]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    @Override
    public int getConut(Connection connection, String providerId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int getRows = 0;
        String sql = "select count(*) as count from  smbms_bill where providerId = ?";
        Object[] params = {providerId};
        try {
            resultSet = BaseDao.execute(connection, preparedStatement, sql, params, resultSet);
            while (resultSet.next()) {
                getRows = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return getRows;
    }

    /**
     * @Author: ZCZ
     * @Description: 获取订单列表
     * @Date: 2023/3/9
     * @Param: [connection, queryProductName]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    @Override
    public List<Bill> getBillList(Connection connection, Bill bill) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Bill> billLists = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> preSqlPrams = new ArrayList<>();
        if (connection != null) {
            sql.append("select * ,p.proName as providerName from smbms_bill  b  left join smbms_provider p  on  b.providerId=p.id  where 1 = 1 ");
            if (!StringUtils.isNullOrEmpty(bill.getProductName())) {
                sql.append(" and b.productName like ?");
                preSqlPrams.add("%" + bill.getProductName() + "%");
            }
            if (bill.getIsPayment() != 0) {
                sql.append(" and  b.isPayment = ?");
                preSqlPrams.add(bill.getIsPayment());
            }
            if (bill.getProviderId() != 0) {
                sql.append(" and  b.providerId = ?");
                preSqlPrams.add(bill.getProviderId());
            }
            Object[] params = preSqlPrams.toArray();
            try {
                resultSet = BaseDao.execute(connection, preparedStatement, sql.toString(), params, resultSet);
                while (resultSet.next()) {
                    Bill billResult = new Bill();
                    billResult.setId(resultSet.getInt("id"));
                    billResult.setBillCode(resultSet.getString("billCode"));
                    billResult.setProductName(resultSet.getString("productName"));
                    billResult.setProductDesc(resultSet.getString("productDesc"));
                    billResult.setProductUnit(resultSet.getString("productUnit"));
                    billResult.setProductCount(resultSet.getBigDecimal("productCount"));
                    billResult.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                    billResult.setIsPayment(resultSet.getInt("isPayment"));
                    billResult.setCreatedBy(resultSet.getInt("createdBy"));
                    billResult.setCreationDate(resultSet.getDate("creationDate"));
                    billResult.setModifyBy(resultSet.getInt("modifyBy"));
                    billResult.setModifyDate(resultSet.getDate("modifyDate"));
                    billResult.setProviderId(resultSet.getInt("providerId"));
                    billResult.setProviderName(resultSet.getString("providerName"));
                    billLists.add(billResult);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                BaseDao.closeResource(null, preparedStatement, resultSet);
            }
        }
        return billLists;
    }

    /**
     * @Author: ZCZ
     * @Description: 添加订单信息
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    @Override
    public int addBill(Connection connection, Bill bill) {
        PreparedStatement preparedStatement = null;
        int addRows = 0;
        String sql = "insert into  smbms_bill (billCode ,productName , productDesc ,productUnit  ,productCount , totalPrice ,isPayment,createdBy ,creationDate , providerId ) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(), bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(), bill.getCreatedBy(), bill.getCreationDate() , bill.getProviderId()};
        try {
            addRows = BaseDao.execute(connection, preparedStatement, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return addRows;
    }

    /**
     * @Author: ZCZ
     * @Description: 删除订单
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    @Override
    public int delBill(Connection connection, Bill bill) {
        PreparedStatement preparedStatement = null;
        int delBill = 0;
        String sql = "delete  from smbms_bill where id = ?";
        Object[] params = {bill.getId()};
        try {
            delBill = BaseDao.execute(connection, preparedStatement, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return delBill;
    }

    /**
     * @Author: ZCZ
     * @Description: 根据订单主键获取订单信息
     * @Date: 2023/3/10
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    @Override
    public Bill getBillById(Connection connection, String id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Bill billResult = null;
        String sql = "select * ,p.proName as providerName from smbms_bill  b  left join smbms_provider p  on  b.providerId=p.id  where b.id = ?";
        Object[] params = {id};
        try {
            resultSet = BaseDao.execute(connection, preparedStatement, sql, params, resultSet);
            while (resultSet.next()) {
                billResult = new Bill();
                billResult.setId(resultSet.getInt("id"));
                billResult.setBillCode(resultSet.getString("billCode"));
                billResult.setProductName(resultSet.getString("productName"));
                billResult.setProductDesc(resultSet.getString("productDesc"));
                billResult.setProductUnit(resultSet.getString("productUnit"));
                billResult.setProductCount(resultSet.getBigDecimal("productCount"));
                billResult.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
                billResult.setIsPayment(resultSet.getInt("isPayment"));
                billResult.setCreatedBy(resultSet.getInt("createdBy"));
                billResult.setCreationDate(resultSet.getDate("creationDate"));
                billResult.setModifyBy(resultSet.getInt("modifyBy"));
                billResult.setModifyDate(resultSet.getDate("modifyDate"));
                billResult.setProviderId(resultSet.getInt("providerId"));
                billResult.setProviderName(resultSet.getString("providerName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return billResult;
    }

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    @Override
    public int updateBill(Connection connection, Bill bill) {
        PreparedStatement preparedStatement = null;
        int updateRow = 0;
        String sql = "update smbms_bill  set billCode = ? ,productName = ?, productDesc =?,productUnit =? ,productCount =?, totalPrice =?,isPayment = ?,modifyBy =?,modifyDate=?,providerId = ?  where id =?";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(), bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(), bill.getModifyBy(), bill.getModifyDate(), bill.getProviderId(), bill.getId()};
        try {
            updateRow = BaseDao.execute(connection, preparedStatement, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRow;
    }
}
