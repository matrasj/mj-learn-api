package com.example.mjlearnapi.question.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Table(name = "question_answer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class QuestionAnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "content", nullable = false)
    String content;
    @Column(name = "question_id")
    Long questionId;
    @Column(name = "correct", nullable = false)
    boolean correct;
}
