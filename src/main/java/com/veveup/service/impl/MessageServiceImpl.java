package com.veveup.service.impl;

import com.veveup.dao.MessageDao;
import com.veveup.domain.Message;
import com.veveup.service.MessageServiceDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageServiceDao {

    @Autowired
    MessageDao messageDao;

    @Override
    public List<Message> findAll() {
        return messageDao.findAll();
    }

    @Override
    public void insertMessage(Message message) {
        messageDao.InsertMessage(message);

    }
}
