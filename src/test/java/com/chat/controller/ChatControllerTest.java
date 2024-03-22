package com.chat.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chat.model.ErrorResponse;
import com.chat.model.MessageDTO;
import com.chat.service.ChatService;

public class ChatControllerTest {

	@InjectMocks
	ChatController chatController;

	@Mock
	ChatService chatService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initializeChatRoomTest() {
		chatController.initializeChatRoom();
		assertTrue(Boolean.TRUE);
	}

	@Test
	public void sendMessageTest() {
		MessageDTO msgDto = MessageDTO.builder().content("Hi").build();
		when(chatService.sendMessage(msgDto)).thenReturn(msgDto);
		ResponseEntity<Object> response = chatController.sendMessage(msgDto);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
		assertEquals(response.getBody(), msgDto);
	}

	@Test
	public void sendMessageInvalidTest() {
		MessageDTO msgDto = MessageDTO.builder().content("Hi").build();
		when(chatService.sendMessage(msgDto)).thenReturn(null);
		ResponseEntity<Object> response = chatController.sendMessage(msgDto);
		assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatusCodeValue());
		assertEquals(((ErrorResponse)response.getBody()).getErrorMsg(), "Not Saved");
	}

	@Test
	public void getAllMessagesTest() {
		List<MessageDTO> messages = new ArrayList<>();
		when(chatService.getAllMessages()).thenReturn(messages);
		ResponseEntity<List<MessageDTO>> response = chatController.getAllMessages();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(response.getBody(), messages);
	}

	@Test
	public void deleteMessageTest() {
		MessageDTO msgDto = MessageDTO.builder().content("Hi").build();
		String responseMsg = "Deleted Successfully";
		when(chatService.deleteMessage(1L, msgDto)).thenReturn(responseMsg);

		ResponseEntity<Object> response = chatController.deleteMessage(1L, msgDto);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(response.getBody(), responseMsg);
	}
}
