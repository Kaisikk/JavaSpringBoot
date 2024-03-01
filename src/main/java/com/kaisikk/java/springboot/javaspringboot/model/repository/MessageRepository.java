package com.kaisikk.java.springboot.javaspringboot.model.repository;

import com.kaisikk.java.springboot.javaspringboot.model.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
