package com.rolling.domain.board;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB PK 생성 전략 그대로
    Integer boardId;

    @Column(name = "member_id", nullable = false)
    String memberId;

    @Column(name = "board_title", nullable = false)
    String boardTitle;

    @Column(name = "board_content", nullable = false)
    String boardContent;

    @Builder
    public Board(Integer boardId, String memberId, String boardTitle, String boardContent) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
