package com.ps.spring_ai.images.services;

import com.ps.spring_ai.record.Question;
import org.springframework.web.multipart.MultipartFile;

public interface OpenAIImageService {

    byte[] getImage(Question question);

    String getDescription(MultipartFile file);
}
