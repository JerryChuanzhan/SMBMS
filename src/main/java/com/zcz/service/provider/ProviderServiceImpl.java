package com.zcz.service.provider;

import com.zcz.dao.BaseDao;
import com.zcz.dao.provider.ProviderDao;
import com.zcz.dao.provider.ProviderDaoImpl;
import com.zcz.entity.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName: ProviderServiceImpl
 * @Description: 供应商业务实现层
 * @Author: ZCZ
 * @Date: 2023/3/8 15:05
 */
public class ProviderServiceImpl implements ProviderService {

    // 引入Dao层
    private ProviderDao providerDao;

    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
    }

    /**
     * @Author: ZCZ
     * @Description: 获取供应商列表
     * @Date: 2023/3/8
     * @Param: [queryProCode, queryProName]
     * @return: [java.lang.String, java.lang.String]
     **/
    @Override
    public List<Provider> getProviderList(String queryProCode, String queryProName) {
        PreparedStatement preparedStatement = null;
        Connection connection = BaseDao.getConnection();
        List<Provider> providerList = providerDao.getProviderList(connection, queryProCode, queryProName);
        BaseDao.closeResource(connection, preparedStatement, null);
        return providerList;
    }

    /**
     * @Author: ZCZ
     * @Description: 添加供应商信息
     * @Date: 2023/3/8
     * @Param: [provider]
     * @return: [com.zcz.entity.Provider]
     **/
    @Override
    public boolean addProvider(Provider provider) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            // 开启事务
            connection.setAutoCommit(false);
            int i = providerDao.addProvider(connection, provider);
            if (i > 0) {
                flag = true;
            }
            // 事务提交
            connection.commit();

        } catch (SQLException e) {
            try {
                // 执行异常，事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * @Author: ZCZ
     * @Description: 删除供应商
     * @Date: 2023/3/8
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    @Override
    public boolean delProvider(String id) {
        PreparedStatement preparedStatement = null;
        boolean delFlag = false;
        Connection connection = BaseDao.getConnection();
        int delRows = providerDao.delProvider(connection, id);
        if (delRows > 0) {
            delFlag = true;
        }
        return delFlag;
    }

    /**
     * @Author: ZCZ
     * @Description: 获取供应商信息
     * @Date: 2023/3/8
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    @Override
    public Provider getProviderById(String id) {
        PreparedStatement preparedStatement = null;
        Connection connection = BaseDao.getConnection();
        Provider provider = providerDao.getProviderById(connection, id);
        BaseDao.closeResource(connection, preparedStatement, null);
        return provider;

    }

    /**
     * @Author: ZCZ
     * @Description: 修改供应商信息
     * @Date: 2023/3/8
     * @Param: [provider]
     * @return: [com.zcz.entity.Provider]
     **/
    @Override
    public boolean updateProvider(Provider provider) {
        PreparedStatement preparedStatement = null;
        Connection connection = BaseDao.getConnection();
        int updateRows = 0;
        boolean updateFlag = false;
        try {
            // 开启事务
            connection.setAutoCommit(false);
            updateRows = providerDao.updateProvider(connection, provider);
            if (updateRows > 0) {
                updateFlag = true;
            }
            // 事务提交
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
        return updateFlag;
    }
}
