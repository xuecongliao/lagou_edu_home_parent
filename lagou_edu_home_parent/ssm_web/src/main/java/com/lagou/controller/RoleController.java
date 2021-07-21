package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role){
        List<Role> allRole = roleService.findAllRole(role);
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有角色成功",allRole);
        return responseResult;
    }


    /*查询所有父子菜单
    * （分配菜单的第一个接口）*/
    @Autowired
    private MenuService menuService;
    @RequestMapping("/findAllMenu")
    public ResponseResult findSubMenuListByPid(){
        //所有父级菜单的parend_id是-1
        List<Menu> MenuList = menuService.findSubMenuListByPid(-1);
        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList",MenuList);
        ResponseResult responseResult = new ResponseResult(true,200,"查询所有父子菜单成功",map);
        return responseResult;

    };

    /*
    根据角色ID查询菜单信息
    */
    @RequestMapping("/findMenuByRoleId")
    ResponseResult findMenuByRoleId(Integer roleId){
        List<String> menuByRoleId = roleService.findMenuByRoleId(roleId);
        ResponseResult responseResult = new ResponseResult(true,200,"查询角色关联的菜单信息成功",menuByRoleId);
        return responseResult;

    };

    /*为角色分配菜单*/
    @RequestMapping("/RoleContextMenu")
    ResponseResult RoleContextMenu(@RequestBody RoleMenuVo roleMenuVo){
        roleService.roleContextMenu(roleMenuVo);
        return new ResponseResult(true,200,"角色分配菜单成功",null);
    }

    /*删除角色*/
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id) {
        roleService.deleteRole(id);
        return new ResponseResult(true,200,"删除角色成功",null);
    }

}
