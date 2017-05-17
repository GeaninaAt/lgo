package com.project.lango.repository;

import com.project.lango.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestion(Long questionId);

    List<Answer> findByQuestionId(Long questionId);


}
