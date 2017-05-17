package com.project.lango.repository;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(name = "findByCategory", value = "select q from Question as q where q.category = :category")
    List<Question> getAllByCategory(@Param("category") Question.Category category);

    List<Question> findByQuizId(Long quizId);

}
