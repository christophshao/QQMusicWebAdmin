package com.javaclient.qqmusicwebserver.service.impl;

import com.javaclient.qqmusicwebserver.dao.ConsumerMapper;
import com.javaclient.qqmusicwebserver.domain.Consumer;
import com.javaclient.qqmusicwebserver.domain.Singer;
import com.javaclient.qqmusicwebserver.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
*用户service实现类
* */
@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public boolean insertConsumer(Consumer consumer) {
        return consumerMapper.insertConsumer(consumer)>0;
    }

    @Override
    public boolean deleteConsumer(Integer id) {
        return consumerMapper.deleteConsumer(id)>0;
    }

    @Override
    public boolean updateConsumer(Consumer consumer) {
        return consumerMapper.updateConsumer(consumer)>0;
    }

    @Override
    public Consumer selectConsumerById(Integer id) {
        return consumerMapper.selectConsumerById(id);
    }

    @Override
    public List<Consumer> selectAllConsumer() {
        return consumerMapper.selectAllConsumer();
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username,password) >0;
    }

    @Override
    public Consumer selectConsumerByUsername(String username) {
        return consumerMapper.selectConsumerByUsername(username);
    }
}
