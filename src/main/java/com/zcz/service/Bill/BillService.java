package com.zcz.service.Bill;

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


}
