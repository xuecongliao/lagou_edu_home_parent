package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    /*分页查询广告信息*/
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage( PromotionAdVO promotionAdVO){
        //该方法get请求无请求体，不需要@RequestBody
        PageInfo<PromotionAd> PageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);
        return  new ResponseResult(true,200,"分页成功",PageInfo);
    }

    /*图片上传*/
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接受到的长传文件是否为空
        if (file.isEmpty()){
            throw new RuntimeException();
        }
        //2.通过HttpServletRequest获取项目部署路径，
        // D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        System.out.println("项目部署路径"+realPath);
        // D:\apache-tomcat-8.5.56\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm-web"));
        System.out.println(substring);
        //3.获取文件的原名
        String originalFilename = file.getOriginalFilename();
        //4.生成新文件名
        String newFileName = System.currentTimeMillis()+originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(newFileName);
        //5.文件上传
        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在，则创建
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录"+filePath);
        }
        file.transferTo(filePath);
        //6.将文件名和文件路径返回，响应
        HashMap<String, String> map = new HashMap<>();
        map.put("filePath","http://loadhost:8080/upload"+newFileName);
        System.out.println(map);
        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传完成", map);
        return responseResult;
    }

    /*广告动态上下线*/
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(int id, int status){

        promotionAdService.updatePromotionAdStatus(id,status);
        return  new ResponseResult(true,200,"分页成功",null);

    }
}
