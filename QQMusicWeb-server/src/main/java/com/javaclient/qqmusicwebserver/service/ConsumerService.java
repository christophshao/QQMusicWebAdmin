package com.javaclient.qqmusicwebserver.service;

import com.javaclient.qqmusicwebserver.domain.Consumer;
import com.javaclient.qqmusicwebserver.domain.Singer;

import java.util.List;

public interface ConsumerService {
    //  添加用户
    public boolean insertConsumer(Consumer consumer);

    //  删除用户
    public boolean deleteConsumer(Integer id);

    //   修改歌手
    public boolean updateConsumer(Consumer consumer);

    //   根据id查询歌手
    public Consumer selectConsumerById(Integer id);

    //   查询所有用户
    public List<Consumer> selectAllConsumer();

    //  验证密码是否正确
    public boolean verifyPassword(String username,String password);

    //   根据账号查询
    public Consumer selectConsumerByUsername(String username);

}
