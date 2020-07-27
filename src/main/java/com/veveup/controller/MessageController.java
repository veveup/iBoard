package com.veveup.controller;

import com.veveup.dao.MessageDao;
import com.veveup.domain.Message;
import com.veveup.utils.DateConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageDao messageDao;

    @RequestMapping("/getAll")
    public String  getAll(Model model) {
        System.out.println("message/getAll run");
        List<Message> all = messageDao.findAll();
        Collections.reverse(all);
        for (Message m :
                all) {
//            System.out.println("Date:"+m.getDate());
            m.setDate(DateConvertUtils.getNiceTime(m.getDate()));
            // 若m.date 字符串长度大于 10 则代表已经大于了一天 返回的是日期 后面的内容不再需要遍历
            if (m.getDate().length() > 10) {
                break;
            }
        }
        model.addAttribute("list", all);
        return "messages";
    }

//    @RequestMapping("/getOne")
//    public String getOne(Integer i,Model model){
//        Message messageById = messageDao.findMessageById(i);
//        model.addAttribute("message",messageById);
//        return "message";
//    }

    @RequestMapping("/save")
    public void saveMessage(String author, String content, HttpServletResponse response, HttpServletRequest request) throws IOException {
        System.out.println("message/save run");
        Message message1 = new Message();
        message1.setAuthor(author);
        message1.setContent(content);
//        message1.setDate(new Date().toString());
        messageDao.InsertMessage(message1);
        System.out.println("message/save Dao done");
        response.sendRedirect(request.getContextPath() + "/message/getAll");
        return;
    }
}
