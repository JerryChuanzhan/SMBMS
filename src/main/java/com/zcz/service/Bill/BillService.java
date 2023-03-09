package com.zcz.service.Bill;

import com.zcz.entity.Bill;

import java.util.List;

/**
 * @InterfaceName: BillService
 * @Description: 订单业务层
 * @Author: ZCZ
 * @Date: 2023/3/8 18:46
 */
public interface BillService {
    /**
     * @Author: ZCZ
     * @Description: 查询供应商下对应的订单数量
     * @Date: 2023/3/8
     * @Param: [providerId]
     * @return: [java.lang.String]
     **/
    public int getConut(String providerId);


    /**
     * @Author: ZCZ
     * @Description: 获取订单列表
     * @Date: 2023/3/9
     * @Param: [queryProductName]
     * @return: [java.lang.String]
     **/
    public List<Bill> getBillList(Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 添加新订单信息
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    public boolean addBill(Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 删除订单
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    public int delBill(Bill bill);

    /**
     * @Author: ZCZ
     * @Description: 根据Id获取订单信息
     * @Date: 2023/3/9
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    public Bill getBillById(String id);

    /**
     * @Author: ZCZ
     * @Description: 修改订单信息
     * @Date: 2023/3/9
     * @Param: [bill]
     * @return: [com.zcz.entity.Bill]
     **/
    public boolean updateBill(Bill bill);
}
