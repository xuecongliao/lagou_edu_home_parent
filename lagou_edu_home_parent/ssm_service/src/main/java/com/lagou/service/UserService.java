package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;

import java.util.List;

public interface UserService {
    /*用户分页&多条件查询*/
    public PageInfo findAllUserByPage(UserVO userVO);

    /*用户登录（根据用户名查询具体的用户信息）*/
    User Login(User user) throws Exception;

    /*回显  根据用户id查询对应的角色信息*/
    List<Role> findUserRelationRoleById(Integer id);

    /*用户关联角色*/
    void userContextRole(UserVO userVO);

    /*获取用户权限 进行菜单动态展示*/
    ResponseResult getUserPermissions(Integer userid);
}
