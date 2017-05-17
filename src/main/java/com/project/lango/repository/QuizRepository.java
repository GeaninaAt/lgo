package com.project.lango.repository;

import com.project.lango.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{
}
