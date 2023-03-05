package com.rolling.service.message;

import com.rolling.domain.message.Message;
import com.rolling.domain.message.MessageRepository;
import com.rolling.domain.message.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;


    /**
     * 메시지 리스트 보기
     * */
    public List<Message> getMessageList() {
        return messageRepository.findAll().stream()
                .filter(e -> e.getMessageState()== MessageState.OPEN)   // 열린 것만 가져오기
                .collect(Collectors.toList());
    }

    /**
     * 메시지 검색하기
     * */

    /**
     * 메시지 작성하기
     * */
    public Long writeMessage(Message message) {
        messageRepository.save(message);
        return message.getId();
    }


    /**
     * 메시지 수정하기
     * */
    public long updateMessage(long messageId, Message message) {
        Optional<Message> byId = messageRepository.findById((int) messageId);
        if (!byId.isEmpty()) {
            byId.get().modify(message);
            return byId.get().getId();
        } else {
            return -1L;
        }
    }

    /**
     * 메시지 삭제하기
     * */

    /**
     * 메시지 차단하기
     * */
}
