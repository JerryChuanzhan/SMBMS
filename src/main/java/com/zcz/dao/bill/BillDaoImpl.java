package com.zcz.dao.bill;

import com.zcz.dao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
