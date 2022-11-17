package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.AdminMapper;
import com.javaclient.qqmusicwebserver.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
* 管理员service实现类
* */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean verifyPassword(String username, String password) {
        return adminMapper.verifyPassword(username,password)>0;
    }
}
