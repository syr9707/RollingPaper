package com.rolling.domain.message;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="message")
public class Message {
    @Id
    @Column(name = "messageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer messageId;

    @Column(name = "board_id",nullable = false)
    Integer boardId;

    @Column(name = "writer", nullable = false, length = 100)
    String writer;

    @Column(name = "message_contents", nullable = false, length = 500)
    String messageContents;

    @Column(name = "message_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageState messageState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="reg_date", nullable=false)
    private Date regDate;

    @Builder
    public Message(Integer id, Integer boardId, String writer, String messageContents) {
        this.messageId = id;
        this.boardId = boardId;
        this.writer = writer;
        this.messageContents = messageContents;
        this.messageState = MessageState.OPEN;
    }


}
