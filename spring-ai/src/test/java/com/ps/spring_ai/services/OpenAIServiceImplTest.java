package com.ps.spring_ai.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIService openAIService;
    //@Test
    void getAnswer() {
        String question = "It takes one person 5 hours to dig a 10 foot hole in the ground. How long would it take 5 people?";
        String answer = openAIService.getAnswer(question);
        System.out.println("############  got the answer ###################");
        System.out.println(answer);
        System.out.println("############  got the answer ###################");
    }
}