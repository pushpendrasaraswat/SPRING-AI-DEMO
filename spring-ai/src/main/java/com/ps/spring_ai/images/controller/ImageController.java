package com.ps.spring_ai.images.controller;


import com.ps.spring_ai.images.services.OpenAIImageService;
import com.ps.spring_ai.record.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final OpenAIImageService openAIImageService;

    @PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    byte[] getImage(@RequestBody Question question) {
        return openAIImageService.getImage(question);
    }

    @PostMapping(value = "/vision", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> uploadImage(@Validated  @RequestParam("file") MultipartFile file, @RequestParam("name")String name) {
        return ResponseEntity.ok(openAIImageService.getDescription(file));
    }
}
