package com.example.mjlearnapi.question.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionCreationResponse {
    Long id;
    String content;
    List<QuestionAnswerPayload> answers;
}
