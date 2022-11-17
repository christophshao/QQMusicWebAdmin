package com.javaclient.qqmusicwebserver.dao;

import com.javaclient.qqmusicwebserver.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 歌手管理接口
* */
@Repository
public interface SingerMapper {

//  添加歌手
    public int insertSinger(Singer singer);

//  删除歌手
    public int deleteSinger(Integer id);

//   修改歌手
    public int updateSinger(Singer singer);

//   根据id查询歌手
    public Singer selectSingerById(Integer id);

//   查询所有歌手
    public List<Singer> selectAllSinger();

//  根据歌手名字模糊查询歌手
    public List<Singer> selectSingerByName(String name);

}
