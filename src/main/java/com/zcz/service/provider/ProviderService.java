package com.zcz.service.provider;

import com.zcz.entity.Provider;

import java.util.List;

/**
 * @ClassName: ProviderService
 * @Description: 供应商业务层
 * @Author: ZCZ
 * @Date: 2023/3/8 15:03
 */
public interface ProviderService {
    /**
     * @Author: ZCZ
     * @Description: 获取供应商列表
     * @Date: 2023/3/8
     * @Param: [queryProCode, queryProName]
     * @return: [java.lang.String, java.lang.String]
     **/
    public List<Provider> getProviderList(String queryProCode, String queryProName);

    /**
     * @Author: ZCZ
     * @Description: 添加供应商
     * @Date: 2023/3/8
     * @Param: [provider]
     * @return: [com.zcz.entity.Provider]
     **/
    public boolean addProvider(Provider provider);


    /**
     * @Author: ZCZ
     * @Description: 删除供应商
     * @Date: 2023/3/8
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    public boolean delProvider(String id);

    /**
     * @Author: ZCZ
     * @Description: 根据供应商ID 查询供应商信息
     * @Date: 2023/3/8
     * @Param: [id]
     * @return: [java.lang.String]
     **/
    public Provider getProviderById(String id);

    /**
     * @Author: ZCZ
     * @Description: 修改供应商信息
     * @Date: 2023/3/8
     * @Param: [provider]
     * @return: [com.zcz.entity.Provider]
     **/
    public boolean updateProvider(Provider provider);
}
