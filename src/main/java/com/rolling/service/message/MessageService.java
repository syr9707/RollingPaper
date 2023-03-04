package com.rolling.service.message;

import com.rolling.domain.message.Message;
import com.rolling.domain.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessageList() {
        return messageRepository.findAll();
    }

    
}
