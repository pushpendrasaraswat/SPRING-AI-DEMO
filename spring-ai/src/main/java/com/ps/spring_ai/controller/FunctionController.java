package com.ps.spring_ai.controller;

import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.Question;
import com.ps.spring_ai.record.WeatherQuestion;
import com.ps.spring_ai.services.OpenAIFunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FunctionController {

    private final OpenAIFunctionService openAIFunctionService;

    @PostMapping("/weather")
    Answer getWeather(@RequestBody WeatherQuestion question){
        return openAIFunctionService.getWeatherUsingFunction(question);
    }
}
