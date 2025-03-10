package com.example.aiProjects.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;

@Service
public class GroqService {

    private final ChatClient chatClient;

    @Autowired
    public GroqService(@Qualifier("openAiChatModel")  ChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String chat(String message) {
        ChatResponse chatResponse = this.chatClient.prompt().user(message).call().chatResponse();
        String output = chatResponse.getResults().get(0).getOutput().getContent();
        return output;
    }
}
