package com.project.lango.repository;

import com.project.lango.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 5/23/2017.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByQuizId(Long quizId);
}
