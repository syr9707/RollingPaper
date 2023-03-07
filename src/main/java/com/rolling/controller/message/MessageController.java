package com.rolling.controller.message;

import com.rolling.domain.message.Message;
import com.rolling.domain.message.MessageRepository;
import com.rolling.dto.message.MessageDto;
import com.rolling.service.message.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private MessageRepository messageRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index(Model model) {
        List<Message> messageList = messageRepository.findAll();
        model.addAttribute("messageList", messageList);
        return "MessageIndex";
    }

    @GetMapping("/")
    public String hello() {
        return "HelloWorld";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody MessageDto dto){
        messageRepository.save(dto.toEntity());
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String write() {
        return "";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateView() {
        return "";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update() {
        return "";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete() {
        return "";
    }

    @RequestMapping(value = "/bann", method = RequestMethod.GET)
    public String bann() {
        return "";
    }
}
