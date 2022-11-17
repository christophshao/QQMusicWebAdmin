package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.SingerMapper;
import com.javaclient.qqmusicwebserver.domain.Singer;
import com.javaclient.qqmusicwebserver.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 歌手service实现类
* */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    //  添加歌手
    @Override
    public boolean insertSinger(Singer singer) {
        return singerMapper.insertSinger(singer)>0;
    }

//  删除歌手
    @Override
    public boolean deleteSinger(Integer id) {
        return singerMapper.deleteSinger(id)>0;
    }

//   修改歌手信息
    @Override
    public boolean updateSinger(Singer singer) {
        return singerMapper.updateSinger(singer)>0;
    }

//   查询所有歌手
    @Override
    public List<Singer> selectAllSinger() {
        return singerMapper.selectAllSinger();
    }

//   根据名字查询歌手
    @Override
    public List<Singer> selectSingerByName(String name) {
        return singerMapper.selectSingerByName(name);
    }

    @Override
    public Singer selectSingerById(Integer id) {
        return singerMapper.selectSingerById(id);
    }

}
