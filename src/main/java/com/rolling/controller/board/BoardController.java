package com.rolling.controller.board;

import com.rolling.domain.board.Board;
import com.rolling.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/view")
    public String boardView() {
        List<Board> boardList = boardService.show();
        return "";
    }

}
