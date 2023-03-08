package com.zcz.dao.provider;

import com.zcz.entity.Provider;

import java.sql.Connection;
import java.util.List;

/**
 * @InterfaceName: ProviderDao
 * @Description: 供应商Dao
 * @Author: ZCZ
 * @Date: 2023/3/8 14:17
 */
public interface ProviderDao {

    /**
     * @Author: ZCZ
     * @Description: 获取供应商列表
     * @Date: 2023/3/8
     * @Param: [connection, queryProCode, queryProName]
     * @return: [java.sql.Connection, java.lang.String, java.lang.String]
     **/
    public List<Provider> getProviderList(Connection connection, String queryProCode, String queryProName);

    /**
     * @Author: ZCZ
     * @Description: 添加供应商信息
     * @Date: 2023/3/8
     * @Param: [connection, provider]
     * @return: [java.sql.Connection, com.zcz.entity.Provider]
     **/
    public int addProvider(Connection connection, Provider provider);

    /**
     * @Author: ZCZ
     * @Description: 删除供应商
     * @Date: 2023/3/8
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    public int delProvider(Connection connection, String id);

    /**
     * @Author: ZCZ
     * @Description: 根据供应商ID查询供应商信息
     * @Date: 2023/3/8
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    public Provider getProviderById(Connection connection, String id);

    /**
     * @Author: ZCZ
     * @Description: 修改供应商信息
     * @Date: 2023/3/8
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    public int updateProvider(Connection connection, Provider provider);
}
