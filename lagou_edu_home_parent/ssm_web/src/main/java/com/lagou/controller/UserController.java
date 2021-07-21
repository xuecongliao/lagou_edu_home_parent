package com.lagou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController  //@Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /*用户分页&多条件查询*/
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVO userVo) {
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        return new ResponseResult(true ,200,"多条件查询分页成功",pageInfo);

    }

    /*用户登录（根据用户名查询具体的用户信息）
    * get请求*/
    @RequestMapping("/login")
    ResponseResult Login(User user, HttpServletRequest request) throws Exception {
        User loginUser = userService.Login(user);
        if (loginUser!=null){
            //保存用户id及access_token到session中
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id",loginUser.getId());
            //将查询出来的信息传到前台
            HashMap<Object, Object> map = new HashMap<>();
            map.put("access_token",access_token);
            map.put("user_id",loginUser.getId());

            //将查询出来的user信息响应给前台
            map.put("user",loginUser);

            return new ResponseResult(true,1,"登录成功",map);

        }else {
            return new ResponseResult(true,400,"用户名密码错误",null);
        }
    }


    /*回显  根据用户id查询对应的角色信息*/
    @RequestMapping("/findUserRoleById")
    ResponseResult findUserRelationRoleById(Integer id){
        List<Role> roleList = userService.findUserRelationRoleById(id);
        return new ResponseResult(true,200,"分配角色回显成功",roleList);

    }


    /*用户关联分配角色*/
    @RequestMapping("/userContextRole")
    ResponseResult userContextRole(@RequestBody UserVO userVO){
        userService.userContextRole(userVO);
        return new ResponseResult(true,200,"关联分配角色成功",null);
    }

    /*获取用户权限 进行菜单动态展示*/
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){

//获取请求头中的 token
        String token = request.getHeader("Authorization");

//获取session中的access_token
        HttpSession session = request.getSession();
        String access_token = (String)session.getAttribute("access_token");

//判断
if(token.equals(access_token)){
        int user_id = (Integer)session.getAttribute("user_id"); ResponseResult result = userService.getUserPermissions(user_id); return result;
    }else{
        ResponseResult result = new ResponseResult(false,400,"获取失败",""); return result;
    }
}
}
