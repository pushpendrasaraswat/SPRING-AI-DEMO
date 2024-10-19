package com.ps.spring_ai.services;

import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.GetCapitalRequest;
import com.ps.spring_ai.record.GetCapitalWithInfoResponse;
import com.ps.spring_ai.record.Question;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapitalOfStateOrCountry(GetCapitalRequest request);

    Answer getCapitalOfStateOrCountryWithExtraInfo(GetCapitalRequest capitalRequest);

    Answer getCapitalResponseAsJson(GetCapitalRequest capitalRequest);

    GetCapitalWithInfoResponse getCapitalwithInfoResponseAsJson(GetCapitalRequest capitalRequest);
}
