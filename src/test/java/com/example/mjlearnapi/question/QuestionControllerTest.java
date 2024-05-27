package com.example.mjlearnapi.question;

import com.example.mjlearnapi.category.CategoryEntity;
import com.example.mjlearnapi.category.CategoryRepository;
import com.example.mjlearnapi.config.SecurityConfig;
import com.example.mjlearnapi.infrastructure.EntityNotFoundException;
import com.example.mjlearnapi.infrastructure.GlobalExceptionHandler;
import com.example.mjlearnapi.question.payload.QuestionCreationRequest;
import com.example.mjlearnapi.question.payload.QuestionCreationResponse;
import com.example.mjlearnapi.question.payload.QuestionAnswerPayload;
import com.example.mjlearnapi.question.type.QuestionType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static com.example.mjlearnapi.question.type.QuestionType.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuestionController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuestionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QuestionFacade questionFacade;

    @MockBean
    CategoryRepository categoryRepository;

    QuestionCreationRequest questionCreationRequest;
    QuestionCreationResponse questionCreationResponse;

    @BeforeEach
    void setUp() {
        questionCreationRequest = QuestionCreationRequest.builder()
                .content("Sample Question")
                .categoryId(1L)
                .questionType(SINGLE_CHOICE)
                .answers(Collections.singletonList(QuestionAnswerPayload.builder()
                        .content("Sample Answer")
                        .correct(true)
                        .build()))
                .build();

        questionCreationResponse = QuestionCreationResponse.builder()
                .id(1L)
                .content("Sample Question")
                .answers(Collections.singletonList(QuestionAnswerPayload.builder()
                        .id(1L)
                        .content("Sample Answer")
                        .questionId(1L)
                        .correct(true)
                        .build()))
                .build();

        given(categoryRepository.findById(1L)).willReturn(Optional.of(new CategoryEntity()));
        given(questionFacade.createQuestion(ArgumentMatchers.any(QuestionCreationRequest.class))).willReturn(questionCreationResponse);
    }

    @Test
    void createQuestion_ShouldReturnCreatedStatus() throws Exception {
        mockMvc.perform(post("/api/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Sample Question\",\"categoryId\":1,\"questionType\":\"SINGLE_CHOICE\",\"answers\":[{\"content\":\"Sample Answer\",\"correct\":true}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("Sample Question"))
                .andExpect(jsonPath("$.answers[0].content").value("Sample Answer"))
                .andExpect(jsonPath("$.answers[0].correct").value(true));
    }

    @Test
    void createQuestion_ShouldThrowEntityNotFoundException() throws Exception {
        given(categoryRepository.findById(2L)).willReturn(null);

        mockMvc.perform(post("/api/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Sample Question\",\"categoryId\":-1,\"questionType\":\"SINGLE_CHOICE\",\"answers\":[{\"content\":\"Sample Answer\",\"correct\":true}]}"))
                .andExpect(status().isNotFound());
    }
}
