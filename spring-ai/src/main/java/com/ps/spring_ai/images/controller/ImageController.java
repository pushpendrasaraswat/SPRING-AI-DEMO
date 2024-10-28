package com.ps.spring_ai.images.controller;


import com.ps.spring_ai.images.services.OpenAIImageService;
import com.ps.spring_ai.record.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final OpenAIImageService openAIImageService;

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    byte[] getImage(@RequestBody Question question) {
        return openAIImageService.getImage(question);
    }
}
