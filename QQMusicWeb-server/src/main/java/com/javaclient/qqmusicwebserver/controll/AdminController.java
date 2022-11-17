package com.javaclient.qqmusicwebserver.controll;

import com.alibaba.fastjson.JSONObject;
import com.javaclient.qqmusicwebserver.service.AdminService;
import com.javaclient.qqmusicwebserver.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController

public class AdminController {

    @Autowired
    private AdminService adminService;

    /*
    * 判断是否登录成功！
    * */
    @RequestMapping(value = "/admin/login/status",method = RequestMethod.POST)

//  接收前端信息，并且在登录成功的情况下保存到session中
    public Object loginStatus(HttpServletRequest request, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag = adminService.verifyPassword(name,password);
        if(flag){
//          登录成功的情况下提示信息，并保存相关信息
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登陆成功！");
            session.setAttribute(Consts.NAME,name);
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"登陆失败！");
        return jsonObject;
    }
}
