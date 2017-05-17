package com.project.lango.service;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import com.project.lango.domain.Quiz;
import com.project.lango.repository.AnswerRepository;
import com.project.lango.repository.QuestionRepository;
import com.project.lango.repository.QuizRepository;
import com.project.lango.ws.rest.exception.IncompatibleCategoriesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question addQuestion(Question question, Quiz quiz){
        Question q = questionRepository.save(question);

        Quiz correspondingQuiz = quizRepository.findOne(quiz.getId());
        if(correspondingQuiz.getType().toString().equals(question.getCategory().toString())){
            List<Question> questions = correspondingQuiz.getQuestions();
            questions.add(q);
            q.setQuiz(quiz);
            return q;
        }else{
            throw new IncompatibleCategoriesException("Question category does not match quiz category!");
        }

    }

    public void deleteQuestion(Long questionId){
        Question question = questionRepository.findOne(questionId);

        for(Iterator<Answer> iterator = question.getAnswers().iterator(); iterator.hasNext();){
            Answer answer = iterator.next();
            iterator.remove();
            answerRepository.delete(answer);
        }

        Quiz quiz = quizRepository.findOne(question.getQuiz().getId());
        for(Question ques : quiz.getQuestions()){
            if(ques.equals(question)){
                quiz.getQuestions().remove(ques);
            }
        }
        questionRepository.delete(question);
    }


    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

}
