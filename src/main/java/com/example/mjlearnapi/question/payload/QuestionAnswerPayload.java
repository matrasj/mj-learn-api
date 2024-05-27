package com.example.mjlearnapi.question.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionAnswerPayload {
    Long id;
    String content;
    Long questionId;
    boolean correct;
}
