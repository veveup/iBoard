package com.veveup.controller;

import com.veveup.dao.MessageDao;
import com.veveup.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageDao messageDao;

    @RequestMapping("/getAll")
    public String  getAll(Model model){
        List<Message> all = messageDao.findAll();
        model.addAttribute("messages",all);
        return "messages";
    }

    @RequestMapping("/getOne")
    public String getOne(Integer i,Model model){
        Message messageById = messageDao.findMessageById(i);
        model.addAttribute("message",messageById);
        return "message";
    }

    @RequestMapping("/save")
    public String saveMessage(String author,String content){
        Message message1 = new Message();
        message1.setAuthor(author);
        message1.setContent(content);
        message1.setDate(new Date());
        messageDao.InsertMessage(message1);
        return "message";
    }
}
