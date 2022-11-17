package com.javaclient.qqmusicwebserver.service;

import com.javaclient.qqmusicwebserver.domain.ListSong;

import java.util.List;
/*
* 歌单里面的歌曲Service
* */
public interface ListSongService {
    /**
     *增加
     */
    public boolean insertListSong(ListSong listSong);

    /**
     *修改
     */
    public boolean updateListSong(ListSong listSong);


    /**
     * 删除
     */
    public boolean deleteListSong(Integer id);


    /**
     * 根据歌曲id和歌单id删除
     */
    public boolean deleteBySongIdAndSongListId(Integer songId,Integer songListId);

    /**
     * 根据主键查询整个对象
     */
    public ListSong selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌单里面的歌曲
     */
    public List<ListSong> selectAllListSong();

    /**
     * 根据歌单id查询所有的歌曲
     */
    public List<ListSong> selectListSongBySongListId(Integer songListId);
}
