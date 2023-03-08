package com.zcz.dao.provider;

import com.mysql.jdbc.StringUtils;
import com.zcz.dao.BaseDao;
import com.zcz.entity.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProviderDaoImpl
 * @Description: 供应商Dao实现类
 * @Author: ZCZ
 * @Date: 2023/3/8 14:38
 */
public class ProviderDaoImpl implements ProviderDao {


    /**
     * @Author: ZCZ
     * @Description: 获取供应商列表
     * @Date: 2023/3/8
     * @Param: [connection, queryProCode, queryProName]
     * @return: [java.sql.Connection, java.lang.String, java.lang.String]
     **/
    @Override
    public List<Provider> getProviderList(Connection connection, String queryProCode, String queryProName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Object> paramsList = new ArrayList<>();
        ArrayList<Provider> providerList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select  * from smbms_provider where 1 =1");

        if (!StringUtils.isNullOrEmpty(queryProCode)) {
            sql.append("  and proCode = ?");
            paramsList.add(queryProCode);
        }
        if (!StringUtils.isNullOrEmpty(queryProName)) {
            sql.append(" and proName like ?");
            paramsList.add("%" + queryProName + "%");
        }
        Object[] params = paramsList.toArray();
        try {
            resultSet = BaseDao.execute(connection, preparedStatement, sql.toString(), params, resultSet);
            while (resultSet.next()) {
                Provider provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getDate("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getDate("modifyDate"));
                providerList.add(provider);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return providerList;
    }

    /**
     * @Author: ZCZ
     * @Description: 添加供应商信息
     * @Date: 2023/3/8
     * @Param: [connection, provider]
     * @return: [java.sql.Connection, com.zcz.entity.Provider]
     **/
    @Override
    public int addProvider(Connection connection, Provider provider) {
        PreparedStatement preparedStatement = null;
        int addRows = 0;
        String sql = "insert into smbms_provider (proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate ) values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {provider.getProCode(), provider.getProName(), provider.getProDesc(), provider.getProContact(), provider.getProPhone(), provider.getProAddress(), provider.getProFax(), provider.getCreatedBy(), provider.getCreationDate()};
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
     * @Description: 删除供应商
     * @Date: 2023/3/8
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    @Override
    public int delProvider(Connection connection, String id) {
        PreparedStatement preparedStatement = null;
        int rowRows;
        String sql = "delete  from smbms_provider where id = ?";
        Object[] params = {id};
        try {
            rowRows = BaseDao.execute(connection, preparedStatement, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return rowRows;
    }

    /**
     * @Author: ZCZ
     * @Description: 根据供应商ID 获取供应商信息
     * @Date: 2023/3/8
     * @Param: [connection, id]
     * @return: [java.sql.Connection, java.lang.String]
     **/
    @Override
    public Provider getProviderById(Connection connection, String id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Provider provider = null;
        String sql = "select  * from smbms_provider where id = ?";
        Object[] params = {id};
        try {
            resultSet = BaseDao.execute(connection, preparedStatement, sql, params, resultSet);
            while (resultSet.next()) {
                provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getDate("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getDate("modifyDate"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return provider;
    }

    /**
     * @Author: ZCZ
     * @Description: 修改供应商信息
     * @Date: 2023/3/8
     * @Param: [connection, provider]
     * @return: [java.sql.Connection, com.zcz.entity.Provider]
     **/
    @Override
    public int updateProvider(Connection connection, Provider provider) {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;
        String sql = "update smbms_provider set proCode = ? ,proName = ? ,proDesc = ? ,proContact = ? ,proPhone = ?,proAddress = ?,proFax = ?,modifyBy = ?,modifyDate = ? where id = ?";
        Object[] params = {provider.getProCode(), provider.getProName(), provider.getProDesc(), provider.getProContact(), provider.getProPhone(), provider.getProAddress(), provider.getProFax(), provider.getModifyBy(), provider.getModifyDate(), provider.getId()};
        try {
            updateRows = BaseDao.execute(connection, preparedStatement, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            BaseDao.closeResource(null, preparedStatement, null);
        }
        return updateRows;
    }
}
