package com.example.mjlearnapi.question.domain;

import com.example.mjlearnapi.question.type.QuestionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Table(name = "question")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "content")
    String content;
    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    QuestionType questionType;
    @Column(name = "category_id")
    Long categoryId;
}
