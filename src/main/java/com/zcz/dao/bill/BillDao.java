package com.zcz.dao.bill;

import java.sql.Connection;

/**
 * @ClassName: BillDao
 * @Description: 订单Dao层
 * @Author: ZCZ
 * @Date: 2023/3/8 18:39
 */
public interface BillDao {
    /**
     * @Author: ZCZ
     * @Description: 查询供应商下对应的订单数量
     * @Date: 2023/3/8
     * @Param: [connection, providerId]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    public int getConut(Connection connection, String providerId);
}
