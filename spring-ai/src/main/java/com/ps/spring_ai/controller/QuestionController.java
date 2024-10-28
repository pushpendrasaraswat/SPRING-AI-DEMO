package com.ps.spring_ai.controller;

import com.ps.spring_ai.record.*;
import com.ps.spring_ai.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService){
        this.openAIService=openAIService;
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapitalOfStateOrCountry(capitalRequest);
    }

    @PostMapping("/capital-extra-info")
    public Answer getCapitalwithExtraInfo(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapitalOfStateOrCountryWithExtraInfo(capitalRequest);
    }

    @PostMapping("/capital-json")
    public Answer getCapitalResponseAsJson(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapitalResponseAsJson(capitalRequest);
    }

    @PostMapping("/capital-info-json")
    public GetCapitalWithInfoResponse getCapitalwithInfoResponseAsJson(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapitalwithInfoResponseAsJson(capitalRequest);
    }
    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question){
        return openAIService.getAnswer(question);
    }


    @PostMapping("/summary-prompt")
    public Answer getSummaryAccordingToPrompt(@RequestBody Summary summary){
        return openAIService.getSummaryAccordingToPrompt(summary);
    }

}
