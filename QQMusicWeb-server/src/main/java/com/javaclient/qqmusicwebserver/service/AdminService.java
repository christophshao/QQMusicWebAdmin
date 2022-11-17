package com.javaclient.qqmusicwebserver.service;
/*
* 管理员接口
* */

public interface AdminService {
    /*
     * 验证账号密码是否正确
     * */

    public boolean verifyPassword(String username,String password);
}
