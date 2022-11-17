package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.SongMapper;
import com.javaclient.qqmusicwebserver.domain.Song;
import com.javaclient.qqmusicwebserver.service.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 歌曲service实现类
* */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public boolean insertSong(Song song) {
        return songMapper.insertSong(song)>0;
    }

    @Override
    public boolean deleteSong(Integer id) {
        return songMapper.deleteSong(id)>0;
    }

    @Override
    public boolean updateSong(Song song) {
        return songMapper.updateSong(song)>0;
    }

    @Override
    public List<Song> selectAllSong() {
        return songMapper.selectAllSong();
    }

    @Override
    public Song selectByPrimaryKey(Integer id) {
        return songMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Song> selectSongByName(String name) {
        return songMapper.selectSongByName(name);
    }

    @Override
    public List<Song> selectSongBySingerId(String id) {
        return songMapper.selectSongBySingerId(id);
    }
}
