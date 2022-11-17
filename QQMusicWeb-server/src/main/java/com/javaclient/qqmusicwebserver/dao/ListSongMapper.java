package com.javaclient.qqmusicwebserver.dao;

import com.javaclient.qqmusicwebserver.domain.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 歌单歌曲接口
* */
@Repository
public interface ListSongMapper {

//  添加歌单歌曲
    public int insertListSong(ListSong listSong);

//  删除歌单歌曲
    public int deleteListSong(Integer id);

//  根据歌曲id和歌单id删除
    public int deleteBySongIdAndSongListId(Integer songId,Integer songListId);

//   修改歌单歌曲
    public int updateListSong(ListSong listSong);

//   查询所有歌单歌曲
    public List<ListSong> selectAllListSong();

    /**
     * 根据主键查询整个对象
     */
    public ListSong selectByPrimaryKey(Integer id);

//  根据歌单id查询歌单歌曲
    public List<ListSong> selectListSongBySongListId(Integer songListId);

}
