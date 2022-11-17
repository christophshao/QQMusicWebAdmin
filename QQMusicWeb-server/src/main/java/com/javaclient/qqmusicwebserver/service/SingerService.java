package com.javaclient.qqmusicwebserver.service;
/*
* 歌手Singer类接口
* */

import com.javaclient.qqmusicwebserver.domain.Singer;

import java.util.List;

public interface SingerService{
    /*
     * 需要的歌手的属性的值
     * */

    //  添加歌手
    public boolean insertSinger(Singer singer);

    //  删除歌手
    public boolean deleteSinger(Integer id);

    //   修改歌手
    public boolean updateSinger(Singer singer);

    //   查询所有歌手
    public List<Singer> selectAllSinger();

    //  根据歌手名字模糊查询歌手
    public List<Singer> selectSingerByName(String name);

    //   根据id查询歌手
    public Singer selectSingerById(Integer id);

}
