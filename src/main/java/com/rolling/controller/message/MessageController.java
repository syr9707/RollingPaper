package com.rolling.controller.message;

import com.rolling.domain.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<Message> messageList = messageService.getMessageList();
        model.addAttribute("messageList", messageList);
        return "index";
    }
}
