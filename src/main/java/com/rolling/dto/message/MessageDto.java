package com.rolling.dto.message;

import com.rolling.domain.board.BoardRepository;
import com.rolling.domain.message.Message;
import com.rolling.domain.message.MessageState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String title;
    private String content;
    private String author;

    public Message toEntity(){
        return Message.builder()    // 보드 어떻게 넣어야 하지??
                .messageTitle(title)
                .messageContents(content)
                .writer(author)
                .messageState(MessageState.OPEN)
                .build();
    }
}
