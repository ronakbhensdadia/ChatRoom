package com.chat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.model.ChatRoom;
import com.chat.model.Message;
import com.chat.model.MessageDTO;
import com.chat.repository.ChatRoomRepository;
import com.chat.repository.MessageRepository;
import com.chat.service.ChatService;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private MessageRepository messageRepository;
	
	// Initialize the chat room
	@Override
	public void initializeChatRoom() {
		if (chatRoomRepository.count() == 0) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoomRepository.save(chatRoom);
		}
	}

	// Method to send a message
	@Override
	public MessageDTO sendMessage(MessageDTO messageDto) {
		ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoom().getId()).orElse(null);
		if (chatRoom != null) {
			Message message = new Message();
			BeanUtils.copyProperties(messageDto, message);
			message = messageRepository.save(message);
			MessageDTO responseDto = new MessageDTO();
			BeanUtils.copyProperties(message, responseDto);
			return responseDto;
		}
		return null;
	}

	// Method to retrieve all messages
	@Override
	public List<MessageDTO> getAllMessages() {
		List<Message> messages = messageRepository.findAll();
		List<MessageDTO> responseDtoList = new ArrayList<>();
		messages.forEach(m -> {
			MessageDTO msgDTO = new MessageDTO();
			BeanUtils.copyProperties(m, msgDTO);
			responseDtoList.add(msgDTO);
		});
		return responseDtoList;
	}

	// Method to delete msg by id.. checking same user validation also
	@Override
	public String deleteMessage(Long id, MessageDTO messageDto) {
		Message msgRecord = messageRepository.findById(id).orElse(null);
		if (msgRecord != null) {
			if (msgRecord.getSender().equalsIgnoreCase(messageDto.getSender())) {
				messageRepository.deleteById(id);
				return "Message Deleted";
			} else {
				return "User not matched";
			}
		}
		return "Invalid Id";
	}
}
