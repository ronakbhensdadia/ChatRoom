package com.chat.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

	private Long id;
	private String content;
	private String sender;
	private LocalDateTime sentAt;
	private ChatRoom chatRoom;

}