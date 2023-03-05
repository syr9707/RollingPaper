package com.rolling.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
