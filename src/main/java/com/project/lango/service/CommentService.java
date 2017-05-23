package com.project.lango.service;

import com.project.lango.domain.Comment;
import com.project.lango.domain.Quiz;
import com.project.lango.domain.User;
import com.project.lango.repository.CommentRepository;
import com.project.lango.repository.QuizRepository;
import com.project.lango.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by gatomulesei on 5/23/2017.
 */
@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

/*    public Comment addComment(Comment comment, Quiz quiz, User user){
        Quiz savedQuiz = quizRepository.findOne(quiz.getId());
        savedQuiz.getComments().add(comment);
        comment.setQuiz(quiz);
        User currentUser = userRepository.findOne(user.getId());
        currentUser.getComments().add(comment);
        comment.setUser(currentUser);
        return commentRepository.save(comment);
    }*/

    public Comment addComment(Comment comment){
        User currentUser = userRepository.findOne(comment.getUser().getId());
        Quiz currentQuiz = quizRepository.findOne(comment.getQuiz().getId());

        Comment comment1 = setProperties(currentUser, currentQuiz, comment);
        Comment savedComment = commentRepository.save(comment1);

        currentQuiz.getComments().add(savedComment);
        return savedComment;
    }

    private Comment setProperties(User user, Quiz quiz, Comment comment){
        comment.setQuiz(quiz);
        comment.setUser(user);
        return comment;
    }

    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findOne(commentId);
        Quiz quiz = comment.getQuiz();
        quiz.getComments().remove(commentRepository.findOne(commentId));
        commentRepository.findOne(commentId).setQuiz(null);
        commentRepository.findOne(commentId).setUser(null);
        commentRepository.delete(commentId);
    }

    public List<Comment> getAllComments(Long quizId){
        return commentRepository.findByQuizId(quizId);
    }
}
