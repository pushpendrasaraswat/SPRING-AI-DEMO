package com.ps.spring_ai.services;

import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.GetCapitalRequest;
import com.ps.spring_ai.record.GetCapitalWithInfoResponse;
import com.ps.spring_ai.record.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    // to use prompt added this template
    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptInfo;

    @Value("classpath:templates/get-capital-json-format.st")
    private Resource getCapitalPromptJson;

    @Value("classpath:templates/get-capital-format-prompt.st")
    private Resource getCapitalFormatPrompt;

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel){
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate=new PromptTemplate(question);
        Prompt prompt=promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();

    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate=new PromptTemplate(question.question());
        Prompt prompt=promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalOfStateOrCountry(GetCapitalRequest capitalRequest) {
        // Using prompt template to get the answer
        //PromptTemplate promptTemplate=new PromptTemplate(capitalRequest.stateOrCountry());
        //Prompt prompt=promptTemplate.create();
        PromptTemplate promptTemplate=new PromptTemplate(getCapitalPrompt);
        Prompt prompt=promptTemplate.create(Map.of("stateOrCountry",capitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
    @Override
    public Answer getCapitalOfStateOrCountryWithExtraInfo(GetCapitalRequest capitalRequest){
        PromptTemplate promptTemplate=new PromptTemplate(getCapitalPromptInfo);
        Prompt prompt=promptTemplate.create(Map.of("stateOrCountry",capitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalResponseAsJson(GetCapitalRequest capitalRequest){
        PromptTemplate promptTemplate=new PromptTemplate(getCapitalPromptJson);
        Prompt prompt=promptTemplate.create(Map.of("stateOrCountry",capitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GetCapitalWithInfoResponse getCapitalwithInfoResponseAsJson(GetCapitalRequest capitalRequest) {
        // this class is deprecated
        //BeanOutputParser<GetCapitalWithInfoResponse> parser = new BeanOutputParser<>(GetCapitalWithInfoResponse.class);
        //String format = parser.getFormat();
        BeanOutputConverter<GetCapitalWithInfoResponse> converter = new BeanOutputConverter<>(GetCapitalWithInfoResponse.class);
        String format=converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalFormatPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", capitalRequest.stateOrCountry(), "format", format));

        ChatResponse response = chatModel.call(prompt);

        System.out.println(response.getResult().getOutput().getContent());
       // return parser.parse(response.getResult().getOutput().getContent());
        return converter.convert(response.getResult().getOutput().getContent());
    }


}
