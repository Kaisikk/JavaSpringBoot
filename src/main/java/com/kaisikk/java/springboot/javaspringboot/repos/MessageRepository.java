package com.kaisikk.java.springboot.javaspringboot.repos;

import com.kaisikk.java.springboot.javaspringboot.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
