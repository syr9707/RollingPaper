package com.rolling.domain.message;

import com.rolling.domain.board.Board;
import com.rolling.domain.board.BoardRepository;
import com.rolling.dto.message.MessageDto;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class MessageRepositoryTest {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void init() {
        boardRepository.save(Board.builder()
                .boardTitle("test")
                .boardContent("test con")
                .memberId("test")
                .build());
    }

    @AfterEach
    void clean() {
        messageRepository.deleteAll();
//        boardRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        messageRepository.save(Message.builder()
                        .board(boardRepository.findById(1L).get())
                        .messageTitle("테스트 게시글")
                        .messageContents("테스트 본문")
                        .writer("jojoldu@gmail.com")
                        .messageState(MessageState.OPEN)
                        .build());

        //when
        List<Message> postsList = messageRepository.findAll();

        //then
        Message posts = postsList.get(0);
        assertThat(posts.getMessageTitle()).isEqualTo("테스트 게시글");
        assertThat(posts.getMessageContents()).isEqualTo("테스트 본문");
    }

    @Test
    public void BaseTimeEntity_등록 () {
        //given
        LocalDateTime now = LocalDateTime.now();
        messageRepository.save(Message.builder()
                .messageTitle("테스트 게시글")
                .messageContents("테스트 본문")
                .writer("jojoldu@gmail.com")
                .messageState(MessageState.OPEN)
                .build());
        //when
        List<Message> postsList = messageRepository.findAll();

        //then
        Message posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }

}