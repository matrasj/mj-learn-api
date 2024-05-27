package com.example.mjlearnapi.question.payload;

import com.example.mjlearnapi.question.type.QuestionType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionCreationRequest {
    String content;
    @NotNull
    Long categoryId;
    @NotNull
    QuestionType questionType;
    List<QuestionAnswerPayload> answers;
}
