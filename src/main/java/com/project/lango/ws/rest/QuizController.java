package com.project.lango.ws.rest;

import com.project.lango.domain.Question;
import com.project.lango.domain.Quiz;
import com.project.lango.repository.QuizRepository;
import com.project.lango.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@RestController
@RequestMapping(value = "/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @PostConstruct
    public void postInit(){
        if(quizRepository.findAll().size() == 0){
            Quiz quizG = new Quiz();
            quizG.setType(Quiz.Type.GERMAN);

            Quiz quizS = new Quiz();
            quizS.setType(Quiz.Type.SPANISH);

            Quiz quizD = new Quiz();
            quizD.setType(Quiz.Type.DUTCH);

            quizService.addQuiz(quizD);
            quizService.addQuiz(quizS);
            quizService.addQuiz(quizG);

        } else {
            System.out.println("Quizzes already exist.");
        }
    }


    @RequestMapping(value = "/submit/{quizId}", method = RequestMethod.POST)
    public double submitQuiz(@PathVariable Long quizId, @RequestBody List<Boolean> results) {
        Quiz quiz = quizRepository.findOne(quizId);

        List<Question> questions = quiz.getQuestions();
        double score = quiz.getScore();

        for(boolean r : results){
            if(!r){
                score--;
            }
        }

        return score;

        /*double score = 10;
        for (Question q : questions) {
            List<Answer> answers = q.getAnswers();
            for (Answer a : answers) {
                if (!a.isCorrect()) {
                    score = score - 1;
                }
            }
        }*/
    }

    @RequestMapping(value = "/getAllQuizzes", method = RequestMethod.GET)
    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }
}
