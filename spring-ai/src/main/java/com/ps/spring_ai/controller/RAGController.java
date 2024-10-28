package com.ps.spring_ai.controller;


import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.Question;
import com.ps.spring_ai.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RAGController {

    private final OpenAIService openAIService;

    @PostMapping("/ask-rag-question")
    public Answer askAnswerFromRag(@RequestBody Question question){
        return openAIService.askAnswerFromRag(question);
    }

    @PostMapping("/ask-rag-question-metadata")
    public Answer askAnswerFromRagWithMetaData(@RequestBody Question question){
        return openAIService.askAnswerFromRagWithMetaData(question);
    }

    @PostMapping("/ask-rag-question-boat")
    public Answer getAnswerBoat(@RequestBody Question question){
        return openAIService.getAnswerBoat(question);
    }


}
