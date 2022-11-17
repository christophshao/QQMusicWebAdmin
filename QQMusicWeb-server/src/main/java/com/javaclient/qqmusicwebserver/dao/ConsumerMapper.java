package com.javaclient.qqmusicwebserver.dao;

import com.javaclient.qqmusicwebserver.domain.Consumer;
import com.javaclient.qqmusicwebserver.domain.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
* 用户接口
* */
@Repository
public interface ConsumerMapper {

//  添加用户
    public int insertConsumer(Consumer consumer);

//  删除用户
    public int deleteConsumer(Integer id);

//   修改用户
    public int updateConsumer(Consumer consumer);

//   根据id查询歌手
    public Consumer selectConsumerById(Integer id);

//   查询所有用户
    public List<Consumer> selectAllConsumer();

//  验证密码是否正确
    public int verifyPassword(String username,String password);

//   根据用户名模糊查询
    public Consumer selectConsumerByUsername(String username);



}
