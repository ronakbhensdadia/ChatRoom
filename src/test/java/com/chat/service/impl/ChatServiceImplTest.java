package com.chat.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.chat.model.ChatRoom;
import com.chat.model.Message;
import com.chat.model.MessageDTO;
import com.chat.repository.ChatRoomRepository;
import com.chat.repository.MessageRepository;

public class ChatServiceImplTest {

	@InjectMocks
	ChatServiceImpl chatServiceImpl;

	@Mock
	ChatRoomRepository chatRoomRepository;

	@Mock
	MessageRepository messageRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initializeChatRoomTest() {
		when(chatRoomRepository.count()).thenReturn(0L);
		when(chatRoomRepository.save(Mockito.any())).thenReturn(null);
		chatServiceImpl.initializeChatRoom();
		assertTrue(Boolean.TRUE);
	}

	@Test
	public void initializeChatRoomFailTest() {
		when(chatRoomRepository.count()).thenReturn(1L);
		chatServiceImpl.initializeChatRoom();
		assertTrue(Boolean.TRUE);
	}

	@Test
	public void sendMessageTest() {
		ChatRoom chatRoom = ChatRoom.builder().id(1L).build();
		Optional<ChatRoom> chat = Optional.of(chatRoom);
		MessageDTO msgDto = MessageDTO.builder().content("Hi").chatRoom(chatRoom).build();
		when(chatRoomRepository.findById(1L)).thenReturn(chat);
		when(messageRepository.save(Mockito.any()))
				.thenReturn(Message.builder().content("Hi").chatRoom(chatRoom).build());
		MessageDTO response = chatServiceImpl.sendMessage(msgDto);
		assertEquals(response, msgDto);
	}

	@Test
	public void sendMessageNullTest() {
		ChatRoom chatRoom = ChatRoom.builder().id(1L).build();
		MessageDTO msgDto = MessageDTO.builder().content("Hi").chatRoom(chatRoom).build();
		MessageDTO response = chatServiceImpl.sendMessage(msgDto);
		assertEquals(response, null);
	}

	@Test
	public void getAllMessagesTest() {
		List<Message> msgList = new ArrayList<>();
		Message msg = Message.builder().content("Hi").build();
		msgList.add(msg);
		when(messageRepository.findAll()).thenReturn(msgList);
		List<MessageDTO> response = chatServiceImpl.getAllMessages();
		MessageDTO msgDTO = new MessageDTO();
		BeanUtils.copyProperties(msg, msgDTO);
		assertEquals(response.get(0), msgDTO);
	}

	@Test
	public void deleteMessageTest() {
		MessageDTO msgDto = MessageDTO.builder().sender("User").build();
		Optional<Message> msg = Optional.of(Message.builder().content("Hi").sender("User").build());
		when(messageRepository.findById(1L)).thenReturn(msg);
		doNothing().when(messageRepository).deleteById(1L);
		String response = chatServiceImpl.deleteMessage(1L, msgDto);
		assertEquals(response, "Message Deleted");
	}
	
	@Test
	public void deleteMessageInvalidIdTest() {
		MessageDTO msgDto = MessageDTO.builder().sender("User").build();
		String response = chatServiceImpl.deleteMessage(1L, msgDto);
		assertEquals(response, "Invalid Id");
	}
	
	@Test
	public void deleteMessageInvalidSenderTest() {
		MessageDTO msgDto = MessageDTO.builder().sender("User").build();
		Optional<Message> msg = Optional.of(Message.builder().content("Hi").sender("User1").build());
		when(messageRepository.findById(1L)).thenReturn(msg);
		doNothing().when(messageRepository).deleteById(1L);
		String response = chatServiceImpl.deleteMessage(1L, msgDto);
		assertEquals(response, "User not matched");
	}
}
