package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.Md5;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVO userVO) {
        // 使用pageHelper
        PageHelper.startPage(userVO.getCurrentPage(),userVO.getPageSize());
        List<User> allUserByPage = userMapper.findAllUserByPage(userVO);
        PageInfo<User> pageInfo = new PageInfo<User>(allUserByPage);

        System.out.println("总条数："+pageInfo.getTotal());
        System.out.println("总页数："+pageInfo.getPages());
        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("每页显示长度："+pageInfo.getPageSize());
        System.out.println("是否第一页："+pageInfo.isIsFirstPage());
        System.out.println("是否最后一页："+pageInfo.isIsLastPage());

        return pageInfo;
    }


    /*用户登录（根据用户名查询具体的用户信息）*/
    @Override
    public User Login(User user) throws Exception {
        User loginUser = userMapper.Login(user);
        if (loginUser!=null&& Md5.verify(user.getPassword(),"lagou",loginUser.getPassword())){
            return loginUser;
        }else{
            return null;
        }


    }


    /*回显  根据用户id查询对应的角色信息*/
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        List<Role> list = userMapper.findUserRelationRoleById(id);
        return list;
    }


    /*用户关联角色*/
    @Override
    public void userContextRole(UserVO userVO) {
        //根据用户id清空中间表关联关系
        userMapper.deleteUserContextRole(userVO.getUserId());
        //建立关联关系
        for (Integer roleId : userVO.getRoleIdList()) {
            //1.
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVO.getUserId());
            user_role_relation.setRoleId(roleId);

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }

    }


    /*获取用户权限 进行菜单动态展示*/
    @Override
    public ResponseResult getUserPermissions(Integer id) {

//1.获取当前用户拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(id);

//2.获取角色ID,保存到 list
        List<Integer> list = new ArrayList<>();


        for (Role role : roleList) { list.add(role.getId());
        }

//3.根据角色id查询 父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(list);
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubmenuList(subMenu);
        }

//5.获取资源权限
        List<Resource> resourceList = userMapper.findResourceByRoleId(list);
        //6.封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("menuList",parentMenu); //menuList: 菜单权限数据map.put("resourceList",resourceList);//resourceList: 资源权限数据

        ResponseResult result = new ResponseResult(true,200,"响应成功",map); return result;

    }
}
