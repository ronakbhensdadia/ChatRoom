package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chat.model.ChatRoom;
import com.chat.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m FROM Message m WHERE m.chatRoom = :chatRoom")
    List<Message> findAllByChatRoom(ChatRoom chatRoom);

	@Query("DELETE FROM Message m WHERE m.chatRoom.id = :chatRoomId")
    void deleteMsByChatRoomId(Long chatRoomId);
}
