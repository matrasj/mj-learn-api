package com.example.mjlearnapi.question;

import com.example.mjlearnapi.infrastructure.EntityNotFoundException;
import com.example.mjlearnapi.question.domain.QuestionAnswerEntity;
import com.example.mjlearnapi.question.domain.QuestionEntity;
import com.example.mjlearnapi.question.payload.QuestionCreationRequest;
import com.example.mjlearnapi.question.payload.QuestionCreationResponse;
import com.example.mjlearnapi.question.repository.QuestionAnswerRepository;
import com.example.mjlearnapi.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionFacade {
    QuestionRepository questionRepository;
    QuestionAnswerRepository questionAnswerRepository;

    @Transactional
    public QuestionCreationResponse createQuestion(QuestionCreationRequest request) {
        switch (request.getQuestionType()) {
            case SINGLE_CHOICE -> {
                QuestionEntity savedQuestion = questionRepository.save(QuestionEntity.builder()
                        .questionType(request.getQuestionType())
                        .content(request.getContent())
                        .categoryId(request.getCategoryId())
                        .build());
                List<QuestionAnswerEntity> savedQuestionAnswers = questionAnswerRepository.saveAll(request.getAnswers()
                        .stream()
                        .map(questionAnswerPayload -> QuestionAnswerEntity.builder()
                                .content(questionAnswerPayload.getContent())
                                .questionId(savedQuestion.getId())
                                .correct(questionAnswerPayload.isCorrect())
                                .build())
                        .collect(Collectors.toList()));
                return QuestionMapper.mapToQuestionCreationResponse(savedQuestion, savedQuestionAnswers);
            }
        }
        return null;
    }
}
