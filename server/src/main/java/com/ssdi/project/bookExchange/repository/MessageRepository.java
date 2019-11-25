package com.ssdi.project.bookExchange.repository;

import com.ssdi.project.bookExchange.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository  extends JpaRepository<Message, Integer> {
}
