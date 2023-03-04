package com.rolling.controller.board;

import com.rolling.domain.board.Board;
import com.rolling.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String boardView() {
        List<Board> boardList = boardService.show();
        return "";
    }

}
