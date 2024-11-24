package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, UUID> {
    @Query("select q from QuizQuestion q where q.quiz.id = ?1")
    List<QuizQuestion> findAllByQuizId(UUID id);

}