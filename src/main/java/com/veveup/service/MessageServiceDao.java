package com.veveup.service;

import com.veveup.domain.Message;

import java.util.List;

public interface MessageServiceDao {
    List<Message> findAll();

    void insertMessage(Message message);

}
