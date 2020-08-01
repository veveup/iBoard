package com.veveup.controller;

import com.veveup.dao.MessageDao;
import com.veveup.domain.Message;
import com.veveup.domain.User;
import com.veveup.utils.DateConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageDao messageDao;

    @RequestMapping("/getAll")
    public String getAll(Model model, HttpServletRequest request) {
        System.out.println("message/getAll run");
        List<Message> all = null;
        Object user = request.getSession().getAttribute("user");
        if (user instanceof User) {
            if (((User) user).getLevel().equals(User.Admin)) {
                // 管理员 可见所有留言 包括被隐藏的（删除的、审核中的）
                all = messageDao.findAll();
            } else {
                // 获得所有可见的留言信息
                all = messageDao.findAllVisiable();
            }
        } else {
            all = messageDao.findAllVisiable();
        }
        // 反转 根据时间顺序显示
        Collections.reverse(all);
        // 格式化时间 显示为友好的方式
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
        // 判断是否由发言权限 存在频繁操作 等不正常的行为

        Message message1 = new Message();
        message1.setAuthor(author);
        message1.setContent(content);

        Object user = request.getSession().getAttribute("user");
        if (user instanceof User) {
            message1.setAuthorId(((User) user).getUid());
        }
        messageDao.InsertMessage(message1);
        System.out.println("message/save Dao done");
        response.sendRedirect(request.getContextPath() + "/message/getAll");
        return;
    }

    // 以post方式 将json格式message 保存 用于Ajax异步请求
    @RequestMapping(value = "/saveAjax", method = {RequestMethod.POST})
    public @ResponseBody
    Object saveMessagePost(@RequestBody Message message, HttpServletRequest request) {
        System.out.println("message/save.Ajax run");
        // 判断是否由权限
        Object user = request.getSession().getAttribute("user");
        if (user instanceof User) {
            message.setAuthorId(((User) user).getUid());
        }
        messageDao.InsertMessage(message);
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "ok");
        map.put("msg", "添加完成");
        map.put("time", String.valueOf(new Date().getTime()));
        return map;
    }

    @RequestMapping("/deleteById")
    public String deleteById(Integer id, Model model, HttpServletRequest request) {
        // 判断是否有删除权限 自己发的留言 管理员 均可删除 但是游客没有权限
        Object user = request.getSession().getAttribute("user");
        if (user instanceof User) {
            // 管理员 直接允许
            if (((User) user).getLevel().equals(User.Admin)) {
                messageDao.setHiddenById(id);
            } else {
                Message messageById = messageDao.findMessageById(id);
                Integer aid = messageById.getAuthorId();
                if (aid instanceof Integer && aid.equals(((User) user).getUid())) {
                    messageDao.setHiddenById(id);
                    model.addAttribute("msg", "删除成功！");
                    return "success";
                }
            }
        }
        if (true) {
            model.addAttribute("msg", "没有删除权限/只允许删除自己的留言！");
            return "error";
        }
        messageDao.setHiddenById(id);
        model.addAttribute("msg", "删除留言成功！");
        return "success";
    }

    @RequestMapping("/likes")
    public @ResponseBody
    Object likes(Integer id, HttpServletResponse response) {
        // 判断用户是否有点赞权
        System.out.println("message/likes run");
        messageDao.addLikesById(id);
        HashMap map = new HashMap();
        map.put("status", "ok");
        map.put("msg", "success");
        response.setStatus(200);
        return map;
    }


}
