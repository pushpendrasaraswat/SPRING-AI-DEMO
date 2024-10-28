package com.ps.spring_ai.services;

import com.ps.spring_ai.record.*;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Value("classpath:templates/rag-prompt-template.st")
    private Resource ragPromptTemplate;

    @Value("classpath:templates/rag-prompt-template-metadata.st")
    private Resource ragPromptTemplateMetaData;

    @Value("classpath:/templates/system-message.st")
    private Resource systemMessageTemplate;

    private final ChatModel chatModel;
    private final SimpleVectorStore simpleVectorStore;
    public OpenAIServiceImpl(ChatModel chatModel,SimpleVectorStore simpleVectorStore) {
        this.chatModel = chatModel;
        this.simpleVectorStore = simpleVectorStore;
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


    @Override
    public Answer getSummaryAccordingToPrompt(Summary summary){
        PromptTemplate promptTemplate=new PromptTemplate(summary.prompt());
        Prompt prompt=promptTemplate.create(Map.of("text",summary.text()));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer askAnswerFromRag(Question question) {
        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(4));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents",
                String.join("\n", contentList)));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }


    @Override
    public Answer askAnswerFromRagWithMetaData(Question question) {
        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(4));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplateMetaData);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents",
                String.join("\n", contentList)));

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }


    @Override
    public Answer getAnswerBoat(Question question) {
        PromptTemplate systemMessagePromptTemplate = new SystemPromptTemplate(systemMessageTemplate);
        Message systemMessage = systemMessagePromptTemplate.createMessage();

        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest
                .query(question.question()).withTopK(5));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Message userMessage = promptTemplate.createMessage(Map.of("input", question.question(), "documents",
                String.join("\n", contentList)));

        ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));

        return new Answer(response.getResult().getOutput().getContent());
    }

}
