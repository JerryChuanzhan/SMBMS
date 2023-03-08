package com.zcz.service.Bill;

import com.zcz.dao.BaseDao;
import com.zcz.dao.bill.BillDao;
import com.zcz.dao.bill.BillDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
}
