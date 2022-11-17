package com.javaclient.qqmusicwebserver.service;
/*
* 歌曲Song类接口
* */

import com.javaclient.qqmusicwebserver.domain.Song;
import java.util.List;

public interface SongService {

    //  添加歌曲
    public boolean insertSong(Song song);

    //  删除歌曲
    public boolean deleteSong(Integer id);

    //   修改歌曲
    public boolean updateSong(Song song);

    //   查询所有歌曲
    public List<Song> selectAllSong();

    //根据主键查询整个对象
    public Song selectByPrimaryKey(Integer id);

    //  根据歌曲名字精确查询歌曲
    public List<Song> selectSongByName(String name);

    //  根据歌手id查询歌曲
    public List<Song> selectSongBySingerId(String id);

}
