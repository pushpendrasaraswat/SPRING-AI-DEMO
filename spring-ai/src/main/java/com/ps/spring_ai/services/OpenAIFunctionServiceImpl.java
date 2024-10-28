package com.ps.spring_ai.services;

import com.ps.spring_ai.functions.WeatherServiceFunction;
import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.Question;
import com.ps.spring_ai.record.WeatherQuestion;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenAIFunctionServiceImpl implements OpenAIFunctionService{

    @Value("${ai.ninja.key}")
    private String apiNinjaKey;

    String prompt = "lat:{lat}, lon:{lon}";

    private final OpenAiChatModel openAiChatModel;

    @Override
    public Answer getWeatherUsingFunction(WeatherQuestion question) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(apiNinjaKey))
                        .withName("CurrentWeather")
                        .withDescription("Get the current weather for a location")
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(prompt,Map.of("lat",question.lat(),"lon",question.lon())).createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
    }
}
