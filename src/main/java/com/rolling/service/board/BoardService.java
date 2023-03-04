package com.rolling.service.board;

import com.rolling.domain.board.Board;
import com.rolling.domain.board.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 회원 가입시 자동 1개 생성
    public void create(Board board) {
        boardRepository.save(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

    public List<Board> show() {
        return boardRepository.findAll();
    }

    public Board fileABoard(String memberId) {
        return boardRepository.findAll().stream()
                .filter(e -> e.getMemberId().equals(memberId))
                .findFirst().get();
    }
}
