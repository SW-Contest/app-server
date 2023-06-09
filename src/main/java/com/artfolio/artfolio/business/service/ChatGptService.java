package com.artfolio.artfolio.business.service;

import com.artfolio.artfolio.global.config.ChatGptConfig;
import com.artfolio.artfolio.business.dto.ChatGptDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class ChatGptService {
    public ChatGptDto.Res ask(ChatGptDto.QuestionReq questionReq) {
        return createWebClient(questionReq);
    }

    private ChatGptDto.Res createWebClient(ChatGptDto.QuestionReq questionReq) {
        ChatGptDto.Message message = new ChatGptDto.Message("user", questionReq.getQuestion());

        ChatGptDto.Req req = ChatGptDto.Req.builder()
                .model(ChatGptConfig.MODEL)
                .messages(List.of(message))
                .build();

        return WebClient.create()
                .post()
                .uri(ChatGptConfig.URL)
                .headers(h -> {
                    h.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
                    h.add("Content-Type", "application/json");
                })
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ChatGptDto.Res.class)
                .block();
    }
}
