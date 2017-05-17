package com.project.lango.ws.rest;

import com.project.lango.domain.Answer;
import com.project.lango.domain.Question;
import com.project.lango.domain.Quiz;
import com.project.lango.domain.User;
import com.project.lango.repository.AnswerRepository;
import com.project.lango.repository.QuestionRepository;
import com.project.lango.repository.QuizRepository;
import com.project.lango.repository.UserRepository;
import com.project.lango.service.AnswerService;
import com.project.lango.service.QuestionService;
import com.project.lango.ws.rest.Create.CreateAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ioana on 8/05/2017.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addQuestion/{quizId}", method = RequestMethod.POST)
    public ResponseEntity<?> addQuestion(@RequestBody @Valid Question question, @PathVariable Long quizId){
        Quiz quiz = quizRepository.findOne(quizId);
        try{
            questionService.addQuestion(question, quiz);
            URI location = URI.create("/admin/addQuestion");
            return ResponseEntity.created(location).build();
        }catch(UnsupportedOperationException e){
            return ResponseEntity.badRequest().body(new ObjectError("question.id", "Ba esti prost"));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addAnswers", method = RequestMethod.POST)
        public ResponseEntity<?> addAnswers(@RequestBody @Valid CreateAnswer createAnswer){
        if(!questionRepository.exists(createAnswer.getQuestionId())) {
            return ResponseEntity.badRequest().body(new ObjectError("question.id", "Intrebarea cu acest id nu exista!"));
        }else{
            try{
                Answer answer = new Answer();
                answer.setText(createAnswer.getText());

                List<Answer> answers = answerRepository.findByQuestionId(createAnswer.getQuestionId());

                List<Answer> result = answers.stream()
                                    .filter(Answer::isCorrect)
                                    .collect(Collectors.toList());
                //result.forEach(System.out::println);

                if(createAnswer.isCorrect()) {
                    if(result.size() >= 1){
                        return ResponseEntity.badRequest().body(new ObjectError("answer.correct", "Intrebarea area deja un raspuns corect!"));
                    }else{
                        answer.setCorrect(createAnswer.isCorrect());
                        answer.setQuestion(questionRepository.findOne(createAnswer.getQuestionId()));
                    }
                }else{
                    answer.setCorrect(createAnswer.isCorrect());
                    answer.setQuestion(questionRepository.findOne(createAnswer.getQuestionId()));
                }

                answerService.addAnswerToQuestion(answer.getQuestion().getId(), answer);
                URI location = URI.create("/admin/addAnswers/" + answer.getQuestion().getId());
                return ResponseEntity.created(location).build();
            }catch(UnsupportedOperationException e){
                return ResponseEntity.badRequest().body(new ObjectError("question.id", "Ba esti prost"));
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/deleteQuestion/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId){
            if(!questionRepository.exists(questionId)){
                return ResponseEntity.badRequest().body(new ObjectError("question.id", "Intrebarea cu acest id nu exista!"));
            }else{
                questionService.deleteQuestion(questionId);
                return ResponseEntity.noContent().build();
            }
    }

    @RequestMapping(value = "/allQuestions", method = RequestMethod.GET)
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @RequestMapping(value = "/getQuestion/{questionId}", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable Long questionId){
        return questionRepository.getOne(questionId);
    }


}


