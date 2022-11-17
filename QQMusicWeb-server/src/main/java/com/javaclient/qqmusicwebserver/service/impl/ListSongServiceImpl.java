package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.ListSongMapper;
import com.javaclient.qqmusicwebserver.domain.ListSong;
import com.javaclient.qqmusicwebserver.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public boolean insertListSong(ListSong listSong) {
        return listSongMapper.insertListSong(listSong)>0;
    }

    @Override
    public boolean updateListSong(ListSong listSong) {
        return listSongMapper.updateListSong(listSong)>0;
    }

    @Override
    public boolean deleteListSong(Integer id) {
        return listSongMapper.deleteListSong(id)>0;
    }

    @Override
    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId) {
        return listSongMapper.deleteBySongIdAndSongListId(songId,songListId) > 0;
    }

    @Override
    public ListSong selectByPrimaryKey(Integer id) {
        return listSongMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ListSong> selectAllListSong() {
        return listSongMapper.selectAllListSong();
    }

    @Override
    public List<ListSong> selectListSongBySongListId(Integer songListId) {
        return listSongMapper.selectListSongBySongListId(songListId);
    }
}
