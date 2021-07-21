package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;
import com.lagou.domain.User_Role_relation;

import java.util.List;

public interface RoleMapper {
    /*查询所有角色&条件*/
    public List<Role> findAllRole(Role role);

    /*
    根据角色ID查询菜单信息
    */
    List<String> findMenuByRoleId(Integer roleId);

    /*根据roleid清空中间表得关联信息*/
    void deleteRoleContextMenu(Integer rid);

    /*为角色分配菜单信息*/
    void roleContextMenu(Role_menu_relation role_menu_relation);

    /*删除角色*/
    void deleteRole(Integer roleid);
}
