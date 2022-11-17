package com.javaclient.qqmusicwebserver.controll;

import com.alibaba.fastjson.JSONObject;
import com.javaclient.qqmusicwebserver.domain.ListSong;
import com.javaclient.qqmusicwebserver.service.ListSongService;
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
/*
* 歌单的歌曲管理 Controller
* */

@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;
    /*
    * 给歌单添加歌曲
    * */
    @RequestMapping(value = "/addListSong",method = RequestMethod.POST)
    public Object addListSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String songId = request.getParameter("songId").trim();  //歌曲id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        ListSong listSong = new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));

        boolean flag = listSongService.insertListSong(listSong);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"保存成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"保存失败");
        return jsonObject;
    }

    /*
    *根据歌单id查询歌曲
    *
    * */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return listSongService.selectListSongBySongListId(Integer.parseInt(songListId));
    }


    //   删除歌曲
    @RequestMapping(value = "/deleteListSong",method = RequestMethod.GET)
    public Object deleteListSong(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();                 //歌曲id
        String songListId = request.getParameter("songListId").trim();                 //歌单id
        boolean flag = listSongService.deleteBySongIdAndSongListId(Integer.parseInt(songId),Integer.parseInt(songListId));
        return flag;
    }

//查询所有歌曲
@RequestMapping(value = "/selectAllListSong",method = RequestMethod.GET)
public Object selectAllListSong(HttpServletRequest request){
    return listSongService.selectAllListSong();
}


}
