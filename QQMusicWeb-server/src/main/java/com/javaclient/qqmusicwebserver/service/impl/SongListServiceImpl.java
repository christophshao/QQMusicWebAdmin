package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.SongListMapper;
import com.javaclient.qqmusicwebserver.dao.SongMapper;
import com.javaclient.qqmusicwebserver.domain.Song;
import com.javaclient.qqmusicwebserver.domain.SongList;
import com.javaclient.qqmusicwebserver.service.SongListService;
import com.javaclient.qqmusicwebserver.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 歌曲service实现类
* */
@Service
public class SongListServiceImpl implements SongListService {

    @Autowired
    private SongListMapper songListMapper;

    @Override
    public boolean insertSongList(SongList songList) {
        return songListMapper.insertSongList(songList)>0;
    }

    @Override
    public boolean updateSongList(SongList songList) {
        return songListMapper.updateSongList(songList)>0;
    }

    @Override
    public boolean deleteSongList(Integer id) {
        return songListMapper.deleteSongList(id)>0;
    }

    @Override
    public SongList selectSongListById(Integer id) {
        return songListMapper.selectSongListById(id);
    }

    @Override
    public List<SongList> selectAllSongList() {
        return songListMapper.selectAllSongList();
    }

    @Override
    public List<SongList> selectSongListOfTitle(String title) {
        return songListMapper.selectSongListOfTitle(title);
    }

    @Override
    public List<SongList> likeTitle(String title) {
        return songListMapper.likeTitle(title);
    }

    @Override
    public List<SongList> likeStyle(String style) {
        return songListMapper.likeStyle(style);
    }
}
