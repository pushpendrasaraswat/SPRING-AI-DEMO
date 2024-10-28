package com.ps.spring_ai.services;

import com.ps.spring_ai.record.Answer;
import com.ps.spring_ai.record.Question;
import com.ps.spring_ai.record.WeatherQuestion;

public interface OpenAIFunctionService {

    Answer getWeatherUsingFunction(WeatherQuestion question);
}
