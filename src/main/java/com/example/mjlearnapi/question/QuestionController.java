package com.example.mjlearnapi.question;

import com.example.mjlearnapi.category.CategoryRepository;
import com.example.mjlearnapi.infrastructure.EntityNotFoundException;
import com.example.mjlearnapi.question.payload.QuestionCreationRequest;
import com.example.mjlearnapi.question.payload.QuestionCreationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class QuestionController {
    QuestionFacade questionFacade;
    CategoryRepository categoryRepository;
    @PostMapping
    public ResponseEntity<QuestionCreationResponse> createQuestion(@RequestBody @Valid QuestionCreationRequest request) {
        categoryRepository.findById(request.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(questionFacade.createQuestion(request));
    }
}
