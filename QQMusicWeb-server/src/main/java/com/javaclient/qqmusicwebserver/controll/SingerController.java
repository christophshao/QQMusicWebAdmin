package com.javaclient.qqmusicwebserver.controll;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.javaclient.qqmusicwebserver.domain.Singer;
import com.javaclient.qqmusicwebserver.service.AdminService;
import com.javaclient.qqmusicwebserver.service.SingerService;
import com.javaclient.qqmusicwebserver.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingerController {

@Autowired
private SingerService singerService;
/*
* 添加歌手
* */
@RequestMapping(value = "/addSinger",method = RequestMethod.POST)
public Object addSinger(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String name = request.getParameter("name").trim();
    String sex = request.getParameter("sex").trim();
    String pic = request.getParameter("pic").trim();
    String birth = request.getParameter("birth").trim();
    String location = request.getParameter("location").trim();  //地区
    String introduction = request.getParameter("introduction").trim(); //简介
//       把生日转换成Date格式
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = new Date();
    try {
        birthDate = dateFormat.parse(birth);
    } catch (ParseException e) {
        e.printStackTrace();
    }

//       保存到歌手的对象中
    Singer singer = new Singer();
    singer.setName(name);
    singer.setSex(new Byte(sex));
    singer.setPic(pic);
    singer.setBirth(birthDate);
    singer.setLocation(location);
    singer.setIntroduction(introduction);
    boolean flag = singerService.insertSinger(singer);
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
 * 修改歌手
 * */
@RequestMapping(value = "/updateSinger",method = RequestMethod.POST)
public Object updateSinger(HttpServletRequest request){
    JSONObject jsonObject = new JSONObject();
    String id = request.getParameter("id").trim();
    String name = request.getParameter("name").trim();
    String sex = request.getParameter("sex").trim();
    String birth = request.getParameter("birth").trim();
    String location = request.getParameter("location").trim();  //地区
    String introduction = request.getParameter("introduction").trim(); //简介
//       把生日转换成Date格式
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date birthDate = new Date();
    try {
        birthDate = dateFormat.parse(birth);
    } catch (ParseException e) {
        e.printStackTrace();
    }

//       保存到歌手的对象中
    Singer singer = new Singer();
    singer.setId(Integer.parseInt(id));
    singer.setName(name);
    singer.setSex(new Byte(sex));
    singer.setBirth(birthDate);
    singer.setLocation(location);
    singer.setIntroduction(introduction);
    boolean flag = singerService.updateSinger(singer);
    if(flag){
        jsonObject.put(Consts.CODE,1);
        jsonObject.put(Consts.MSG,"修改成功！");
        return jsonObject;
    }
    jsonObject.put(Consts.CODE,0);
    jsonObject.put(Consts.MSG,"修改失败！");
    return jsonObject;

}

//   删除歌手
@RequestMapping(value = "/deleteSinger",method = RequestMethod.GET)
public Object deleteSinger(HttpServletRequest request){
    String id = request.getParameter("id").trim();
    boolean flag = singerService.deleteSinger(Integer.parseInt(id));
    return flag;
}

//查询所有歌手
@RequestMapping(value = "/selectAllSinger",method = RequestMethod.GET)
public Object selectAllSinger(HttpServletRequest request){
    return singerService.selectAllSinger();

}

//根据歌手名字查询所有歌手
@RequestMapping(value = "/selectSingerByName",method = RequestMethod.GET)
    public Object selectSingerByName(HttpServletRequest request){
        String name = request.getParameter("name").trim();
        return singerService.selectSingerByName("%"+name+"%");
    }


//更新歌手图片
    @RequestMapping(value = "/updateSingerPic",method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if (avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败！");
            return jsonObject;
        }
//       文件名 = 当前时间到毫秒 + 原来的文件名
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
//        文件路径
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerPic";
//        如果文件路径不存在，新增路径
        File file1 = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
//      实际的文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
//       存储到数据库里的相对文件地址
        String storeAvatorPath = "img/singerPic/" + fileName;
        try {
            avatorFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean flag = singerService.updateSinger(singer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功!");
                jsonObject.put("pic",storeAvatorPath);
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
