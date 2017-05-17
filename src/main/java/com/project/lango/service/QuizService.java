package com.project.lango.service;

import com.project.lango.domain.Quiz;
import com.project.lango.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@Service
@Transactional
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public Quiz addQuiz(Quiz quiz){
        return quizRepository.save(quiz);
    }

    public Quiz getQuiz(Long quizId){
        return quizRepository.findOne(quizId);
    }


}
