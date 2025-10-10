package com.alibaba.cloud.ai.example.tongyi.service.impl.helloworld;

import com.alibaba.cloud.ai.example.tongyi.service.AbstractTongYiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;

import org.springframework.ai.chat.messages.UserMessage;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TongYiSimpleServiceImpl extends AbstractTongYiServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(TongYiSimpleServiceImpl.class);
	private final ChatClient chatClient;

	private final StreamingChatClient streamingChatClient;

	@Autowired
	public TongYiSimpleServiceImpl(ChatClient chatClient, StreamingChatClient streamingChatClient) {
		this.chatClient = chatClient;
		this.streamingChatClient = streamingChatClient;
	}

	@Override
	public String completion(String message) {

		Prompt prompt = new Prompt(new UserMessage(message));

		return chatClient.call(prompt).getResult().getOutput().getContent();
	}


}
