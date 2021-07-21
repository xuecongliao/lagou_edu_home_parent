package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {
    /*用户分页&多条件查询*/
    public List<User> findAllUserByPage(UserVO userVo);

    /*用户登录（根据用户名查询具体的用户信息）*/
    User Login(User user);

    /*根据用户id查询对应的角色信息*/
    List<Role> findUserRelationRoleById(Integer id);

    /*根据用户id清空中间表*/
    void deleteUserContextRole(Integer userId);

    /*分配角色  向中间表添加记录*/
    void userContextRole(User_Role_relation user_role_relation);


    /*获取用户所具有的权限信息  1.根据用户id查询对应的角色信息  已有*/
    /*获取用户所具有的权限信息  2.根据角色id查询父级菜单*/
    List<Menu> findParentMenuByRoleId(List<Integer> ids);
    /*获取用户所具有的权限信息  3.再对父菜单关联的子菜单进行关联查询*/
    List<Menu> findSubMenuByPid(Integer id);
    /*获取用户所具有的权限信息  4.获取资源信息*/
    List<Resource> findResourceByRoleId(List<Integer> ids);
}
