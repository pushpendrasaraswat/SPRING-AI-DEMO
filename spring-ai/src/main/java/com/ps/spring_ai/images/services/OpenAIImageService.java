package com.ps.spring_ai.images.services;

import com.ps.spring_ai.record.Question;

public interface OpenAIImageService {

    byte[] getImage(Question question);
}
