package com.project.lango.ws.rest;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import com.project.lango.repository.QuestionRepository;
import com.project.lango.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alexandra on 5/10/2017.
 */
@RestController
@RequestMapping(value = "/questions")
@CrossOrigin(origins = "http://localhost:8000")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;


    @RequestMapping(value = "/{category}", method = RequestMethod.GET)
    public List<Question> getByCategory(@PathVariable String category){

        Question.Category questionCategory;

            questionCategory = Question.Category.valueOf(category.toUpperCase());
            return questionRepository.getAllByCategory(questionCategory);
    }


    @RequestMapping(value = "/getAforQ/{questionId}", method = RequestMethod.GET)
    public List<Answer> getAnswersForQuestion(@PathVariable Long questionId){
        return answerService.getAllAnswersForQuestion(questionId);
    }




}
