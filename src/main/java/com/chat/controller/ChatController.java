package com.chat.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.model.ErrorResponse;
import com.chat.model.MessageDTO;
import com.chat.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	// Initialize the chat room upon server startup
	@PostConstruct
	public void initializeChatRoom() {
		chatService.initializeChatRoom();
	}

	// Sending a message
	@PostMapping("/send")
	public ResponseEntity<Object> sendMessage(@RequestBody MessageDTO messageDto) {
		MessageDTO savedMessage = chatService.sendMessage(messageDto);
		if (savedMessage != null) {
			return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(
					ErrorResponse.builder().errorCode(HttpStatus.NOT_ACCEPTABLE.value()).errorMsg("Not Saved").build(),
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// For Retrieving all messages
	@GetMapping("/messages")
	public ResponseEntity<List<MessageDTO>> getAllMessages() {
		List<MessageDTO> messages = chatService.getAllMessages();
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}

	// For deleting a message by ID
	@DeleteMapping("/deleteMsg/{id}")
	public ResponseEntity<Object> deleteMessage(@PathVariable Long id, @RequestBody MessageDTO messageDto) {
		String deleteMsg = chatService.deleteMessage(id, messageDto);
		return new ResponseEntity<>(deleteMsg, HttpStatus.OK);
	}
}
