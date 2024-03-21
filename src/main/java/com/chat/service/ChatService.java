package com.chat.service;

import java.util.List;

import com.chat.model.MessageDTO;

public interface ChatService {

	void initializeChatRoom();

	MessageDTO sendMessage(MessageDTO messageDto);

	List<MessageDTO> getAllMessages();

	String deleteMessage(Long id, MessageDTO messageDto);

}
