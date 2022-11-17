package com.javaclient.qqmusicwebserver.dao;

import com.javaclient.qqmusicwebserver.domain.Singer;
import com.javaclient.qqmusicwebserver.domain.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 歌曲管理接口
* */
@Repository
public interface SongMapper {

//  添加歌曲
    public int insertSong(Song song);

//  删除歌曲
    public int deleteSong(Integer id);

//   修改歌曲
    public int updateSong(Song song);

//   查询所有歌曲
    public List<Song> selectAllSong();

//
    public Song selectByPrimaryKey(Integer id);

//  根据歌手名字精确查询歌曲
    public List<Song> selectSongByName(String name);

//  根据歌手id查询歌曲
    public List<Song> selectSongBySingerId(String id);

}
