package com.ps.spring_ai.audio.controller;

import com.ps.spring_ai.audio.services.AudioAiService;
import com.ps.spring_ai.record.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RequiredArgsConstructor
@RestController
public class AudioController {

    private final AudioAiService audioAiService;

    @PostMapping(value = "/audio", produces = "audio/mpeg")
    byte[] textToSpeech(@RequestBody Question question){
        return audioAiService.textToSpeech(question);
    }
}
