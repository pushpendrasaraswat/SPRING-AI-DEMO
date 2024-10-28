package com.ps.spring_ai.images.services;

import com.ps.spring_ai.record.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiImageServciceImpl implements OpenAIImageService {

    private final ImageModel imageModel;
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
}
