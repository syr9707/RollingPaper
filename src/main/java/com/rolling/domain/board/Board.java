package com.rolling.domain.board;

import com.rolling.domain.message.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board")
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB PK 생성 전략 그대로
    private Long boardId;

//    @OneToOne
//    @JoinColumn(name = "member_id")
//    Member member;

    @Column(name = "member_id", nullable = false)   // FK - Member
    private String memberId;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @OneToMany (mappedBy = "board_id", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

    @Builder
    public Board(Long boardId, String memberId, String boardTitle, String boardContent) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
