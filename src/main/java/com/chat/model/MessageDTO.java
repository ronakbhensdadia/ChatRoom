package com.chat.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

	private Long id;
	private String content;
	private String sender;
	private LocalDateTime sentAt;
	private ChatRoom chatRoom;

}