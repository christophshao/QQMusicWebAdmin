package com.javaclient.qqmusicwebserver.service;
/*
* 歌曲Song类接口
* */

import com.javaclient.qqmusicwebserver.domain.Song;
import com.javaclient.qqmusicwebserver.domain.SongList;

import java.util.List;


public interface SongListService {
    /**
     *增加
     */
    public boolean insertSongList(SongList songList);

    /**
     *修改
     */
    public boolean updateSongList(SongList songList);

    /**
     * 删除
     */
    public boolean deleteSongList(Integer id);

    /**
     * 根据主键查询整个对象
     */
    public SongList selectSongListById(Integer id);

    /**
     * 查询所有歌单
     */
    public List<SongList> selectAllSongList();

    /**
     * 根据标题精确查询歌单列表
     */
    public List<SongList> selectSongListOfTitle(String title);

    /**
     * 根据标题模糊查询歌单列表
     */
    public List<SongList> likeTitle(String title);

    /**
     * 根据风格模糊查询歌单列表
     */
    public List<SongList> likeStyle(String style);
}
