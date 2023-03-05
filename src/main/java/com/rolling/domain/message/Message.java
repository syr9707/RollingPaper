package com.rolling.domain.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rolling.domain.BaseTimeEntity;
import com.rolling.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="message")
public class Message extends BaseTimeEntity {
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board_id;

    @Column(name = "writer", nullable = false, length = 100)
    private String writer;

    @Column(name = "message_Title", nullable = false, length = 200)
    private String messageTitle;

    @Column(name = "message_content", nullable = false, length = 1000)
    private String messageContents;

    @Column(name = "message_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageState messageState;

    @Builder
    public Message(Long id, Board board, String writer, String messageTitle, String messageContents, MessageState messageState) {
        this.id = id;
        this.board_id = board;
        this.writer = writer;
        this.messageTitle = messageTitle;
        this.messageContents = messageContents;
        this.messageState = messageState;
    }
}
