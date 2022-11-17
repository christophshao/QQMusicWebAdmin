package com.javaclient.qqmusicwebserver.dao;

import org.springframework.stereotype.Repository;

/*
* 管理员Dao
* */
@Repository
public interface AdminMapper {
    /*
    * 验证账号密码是否正确
    * */
    public int verifyPassword(String username,String password);

}
