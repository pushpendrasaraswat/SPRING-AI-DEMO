package com.ps.spring_ai.services;

import com.ps.spring_ai.record.*;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapitalOfStateOrCountry(GetCapitalRequest request);

    Answer getCapitalOfStateOrCountryWithExtraInfo(GetCapitalRequest capitalRequest);

    Answer getCapitalResponseAsJson(GetCapitalRequest capitalRequest);

    GetCapitalWithInfoResponse getCapitalwithInfoResponseAsJson(GetCapitalRequest capitalRequest);

    Answer getSummaryAccordingToPrompt(Summary summary);

    Answer askAnswerFromRag(Question question);

    Answer askAnswerFromRagWithMetaData(Question question);

    Answer getAnswerBoat(Question question);
}
