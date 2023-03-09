package com.zcz.dao.bill;

import com.zcz.entity.Bill;

import java.sql.Connection;
import java.util.List;

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


    /**
     * @Author: ZCZ
     * @Description: 获取订单列表
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    public List<Bill> getBillList(Connection connection, Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 添加订单
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    public int addBill(Connection connection, Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 删除订单
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    public int delBill(Connection connection, Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 通过Id查找对应订单
     * @Date: 2023/3/9
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    public Bill getBillById(Connection connection, String id);

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [connection, bill]
     * @return: [java.sql.Connection, com.zcz.entity.Bill]
     **/
    public int updateBill(Connection connection, Bill bill);
}
