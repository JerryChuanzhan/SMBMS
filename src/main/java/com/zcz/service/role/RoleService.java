package com.zcz.service.role;

import com.zcz.entity.Role;

import java.util.List;

/**
 * @Description: 角色业务层
 * @ClassName: RoleService
 * @Author: ZCZ
 * @Date 2023年03月02日 22:50
 */
public interface RoleService {
    /**
     * @Description: 获取角色列表
     * @Date: 2023/3/5
     * @Param: []
     * @return: java.util.List<com.zcz.entity.Role>
     **/
    public List<Role> getRoleList();
}
