package com.rolling.dto.board;

import com.rolling.domain.board.Board;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private String boardTitle;
    private String boardContent;

    public Board toEntity(){
        return Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .build();
    }
}
