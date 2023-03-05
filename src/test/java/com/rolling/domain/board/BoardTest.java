package com.rolling.domain.board;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test @Order(1)
    void save() {
        Board params = Board.builder()
                .memberId("test")
                .boardTitle("1번 테스트")
                .boardContent("1번 테스트의 롤링페이퍼 입니다.")
                .build();

        boardRepository.save(params);

        Board entity = boardRepository.findById(1).get();
        assertThat(entity.getBoardTitle()).isEqualTo("1번 테스트");
    }

    @Test @Order(2)
    void findAll() {
        long boardsCount = boardRepository.count();
        List<Board> boardList = boardRepository.findAll();
    }

    @Test @Order(3)
    void delete() {
        Board entity = boardRepository.findById(1).get();
        boardRepository.delete(entity);
    }
}