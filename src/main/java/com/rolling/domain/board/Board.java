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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // DB PK 생성 전략 그대로
    Integer boardId;
    @Column(nullable = false)
    String memberId;
    @Column(nullable = false)
    String boardTitle;
    @Column(nullable = false)
    String boardContent;

    @Builder
    public Board(Integer boardId, String memberId, String boardTitle, String boardContent) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
