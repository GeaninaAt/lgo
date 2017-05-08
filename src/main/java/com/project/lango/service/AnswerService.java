package com.project.lango.service;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import com.project.lango.repository.AnswerRepository;
import com.project.lango.repository.QuestionRepository;
import org.hibernate.sql.ANSICaseFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Service
@Transactional
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public Answer addAnswerToQuestion(Long questionId, Answer answer){

        Question question = questionRepository.findOne(questionId);

        List<Answer> answers = new ArrayList<>();
        answers.add(answer);
        question.setAnswers(answers);
        answerRepository.save(answer);

        return answer;
    }

    public void setCorrectAnswer(Long answerId, Long questionId){
        Answer answer = answerRepository.findOne(answerId);
        Question question = questionRepository.findOne(questionId);

        if(answer.getQuestion() == question){
            answer.setCorrect(true);
        }else{
            System.out.println("Answer not found for this question.");
        }
    }

    public void deleteAnswer(Long answerId, Long questionId){
        Answer answer = answerRepository.findOne(answerId);
        Question question = questionRepository.findOne(questionId);

        if(answer.getQuestion() == question){
            answerRepository.delete(answer);
        }else{
            System.out.println("No answer found for this question");
        }
    }

    public Answer getCorrectAnswer(Long answerId, Long questionId){
        Answer answer = answerRepository.findOne(answerId);
        Question question = questionRepository.findOne(questionId);

        if(answer.getQuestion() == question){
            if(answer.isCorrect()){
                return answer;
            }
        }else{
            System.out.println("No answer found for this question");
        }
        return null;
    }

    public List<Answer> getAllAnswersForQuestion(Long questionId){
        return answerRepository.findByQuestion(questionId);
    }
}