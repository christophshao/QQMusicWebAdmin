package com.javaclient.qqmusicwebserver.controll;

import com.alibaba.fastjson.JSONObject;
import com.javaclient.qqmusicwebserver.domain.Consumer;
import com.javaclient.qqmusicwebserver.service.ConsumerService;
import com.javaclient.qqmusicwebserver.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

@Autowired
private ConsumerService consumerService;
/*
* 添加用户
* */
@RequestMapping(value = "/addConsumer",method = RequestMethod.POST)
public Object addConsumer(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String username = request.getParameter("username").trim();
    String password = request.getParameter("password").trim();
    String sex = request.getParameter("sex").trim();
    String phoneNum = request.getParameter("phoneNum").trim();
    String email = request.getParameter("email").trim();
    String birth = request.getParameter("birth").trim();
    String introduction = request.getParameter("introduction").trim(); //简介
    String location = request.getParameter("location").trim();  //地区
    String avator = request.getParameter("avator").trim();  //头像地址

    if(username == null || "".equals(username)){
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名不能为空！");
        return jsonObject;
    }
    if(password == null || "".equals(password)){
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"密码不能为空！");
        return jsonObject;
    }

//       把生日转换成Date格式
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = new Date();
    try {
        birthDate = dateFormat.parse(birth);
    } catch (ParseException e) {
        e.printStackTrace();
    }

//       保存到用户的对象中
    Consumer consumer = new Consumer();
    consumer.setUsername(username);
    consumer.setPassword(password);
    consumer.setSex(new Byte(sex));
    consumer.setPhoneNum(phoneNum);
    consumer.setEmail(email);
    consumer.setBirth(birthDate);
    consumer.setIntroduction(introduction);
    consumer.setLocation(location);
    consumer.setAvator(avator);

    boolean flag = consumerService.insertConsumer(consumer);
    if(flag){
        jsonObject.put(Consts.CODE,1);
        jsonObject.put(Consts.MSG,"添加成功！");
        return jsonObject;
    }
    jsonObject.put(Consts.CODE,0);
    jsonObject.put(Consts.MSG,"添加失败！");
    return jsonObject;

}

/*
 * 修改用户
 * */
@RequestMapping(value = "/updateConsumer",method = RequestMethod.POST)
public Object updateConsumer(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String username = request.getParameter("username").trim();
    String password = request.getParameter("password").trim();
    String sex = request.getParameter("sex").trim();
    String phoneNum = request.getParameter("phoneNum").trim();
    String email = request.getParameter("email").trim();
    String birth = request.getParameter("birth").trim();
    String introduction = request.getParameter("introduction").trim(); //简介
    String location = request.getParameter("location").trim();  //地区

    if(username == null || "".equals(username)){
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"用户名不能为空！");
        return jsonObject;
    }
    if(password == null || "".equals(password)){
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"密码不能为空！");
        return jsonObject;
    }

//       把生日转换成Date格式
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = new Date();
    try {
        birthDate = dateFormat.parse(birth);
    } catch (ParseException e) {
        e.printStackTrace();
    }

//       保存到用户的对象中
    Consumer consumer = new Consumer();
    consumer.setId(Integer.parseInt(id));
    consumer.setUsername(username);
    consumer.setPassword(password);
    consumer.setSex(new Byte(sex));
    consumer.setPhoneNum(phoneNum);
    consumer.setEmail(email);
    consumer.setBirth(birthDate);
    consumer.setIntroduction(introduction);
    consumer.setLocation(location);
    boolean flag = consumerService.updateConsumer(consumer);

    if(flag){
        jsonObject.put(Consts.CODE,1);
        jsonObject.put(Consts.MSG,"修改成功！");
        return jsonObject;
    }
    jsonObject.put(Consts.CODE,0);
    jsonObject.put(Consts.MSG,"修改失败！");
    return jsonObject;

}

//   删除用户
@RequestMapping(value = "/deleteConsumer",method = RequestMethod.GET)
public Object deleteConsumer(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = consumerService.deleteConsumer(Integer.parseInt(id));
    return flag;
}

//查询所有用户
@RequestMapping(value = "/selectAllConsumer",method = RequestMethod.GET)
public Object selectAllConsumer(HttpServletRequest request){
    return consumerService.selectAllConsumer();

}

/*
* 根据id查询整个对象
* */
@RequestMapping(value = "/selectConsuerById",method = RequestMethod.GET)
public Object selectConsuerById(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    return consumerService.selectConsumerById(Integer.parseInt(id));
}

//根据用户名模糊查询所有用户
@RequestMapping(value = "/selectConsumerByName",method = RequestMethod.GET)
    public Object selectConsumerByName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return consumerService.selectConsumerByUsername("%"+name+"%");
}


//更新用户图片
    @RequestMapping(value = "/updateConsumerPic",method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败！");
            return jsonObject;
        }
//       文件名 = 当前时间到毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
//        文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";
//        如果文件路径不存在，新增路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
//      实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
//       存储到数据库里的相对文件地址
        String storeAvatorPath = "avatorImages/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.updateConsumer(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功!");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败!");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败!"+e.getMessage());
        }finally {
            return jsonObject;
        }


    }


}
