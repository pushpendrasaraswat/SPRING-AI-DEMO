package com.ps.spring_ai.images.services;

import com.ps.spring_ai.record.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiImageServciceImpl implements OpenAIImageService {

    private final ImageModel imageModel;//OpenAIImageModel can also be used which is a subclass of ImageModel
    private final ChatModel chatModel;

    @Override
    public byte[] getImage(Question question) {

        var option=OpenAiImageOptions.builder().withHeight(1024)
                .withWidth(1024)
                .withResponseFormat("b64_json")
                .withModel("dall-e-3")
                .build();

        ImagePrompt imagePrompt=new ImagePrompt(question.question(),option);
        var imageResponse=imageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }


    @Override
    public String getDescription(MultipartFile file){
        var option= OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_O.getValue())
                .build();

        var userMessage=new UserMessage( "Explain what do you see in this picture?", List.of(new Media(MimeTypeUtils.IMAGE_PNG,file.getResource())));

        ChatResponse response=chatModel.call(new Prompt(List.of(userMessage),option));

        return response.getResult().getOutput().getContent();



    }
}
