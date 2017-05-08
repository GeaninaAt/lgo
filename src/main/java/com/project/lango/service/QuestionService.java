package com.project.lango.service;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import com.project.lango.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(Question question){
        Question q = questionRepository.save(question);
        return q;
    }

    public void deleteQuestion(Long questionId){
        Question q = questionRepository.findOne(questionId);
        questionRepository.delete(q);
    }

    public Question getQuestion(Long questionId){
        return questionRepository.findOne(questionId);
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }


}
