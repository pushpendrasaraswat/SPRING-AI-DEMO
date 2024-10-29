package com.ps.spring_ai.audio.services;

import com.ps.spring_ai.record.Question;

public interface AudioAiService {


    byte[] textToSpeech(Question question);
}
