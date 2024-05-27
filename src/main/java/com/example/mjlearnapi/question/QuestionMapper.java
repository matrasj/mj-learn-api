package com.example.mjlearnapi.question;

import com.example.mjlearnapi.question.domain.QuestionAnswerEntity;
import com.example.mjlearnapi.question.domain.QuestionEntity;
import com.example.mjlearnapi.question.payload.QuestionAnswerPayload;
import com.example.mjlearnapi.question.payload.QuestionCreationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {
    public static QuestionCreationResponse mapToQuestionCreationResponse(QuestionEntity savedQuestion, List<QuestionAnswerEntity> savedQuestionAnswers) {
        return QuestionCreationResponse.builder()
                .id(savedQuestion.getId())
                .content(savedQuestion.getContent())
                .answers(QuestionMapper.mapToQuestionAnswerPayloadList(savedQuestionAnswers))
                .build();
    }

    public static QuestionAnswerPayload mapToQuestionAnswerPayload(QuestionAnswerEntity questionAnswer) {
        return QuestionAnswerPayload.builder()
                .id(questionAnswer.getQuestionId())
                .content(questionAnswer.getContent())
                .correct(questionAnswer.isCorrect())
                .build();
    }

    public static List<QuestionAnswerPayload> mapToQuestionAnswerPayloadList(List<QuestionAnswerEntity> questionAnswers) {
        return questionAnswers.stream().map(QuestionMapper::mapToQuestionAnswerPayload).collect(Collectors.toList());
    }
}
