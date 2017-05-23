package com.project.lango.ws.rest;

import com.project.lango.domain.Comment;
import com.project.lango.domain.Question;
import com.project.lango.domain.Quiz;
import com.project.lango.domain.User;
import com.project.lango.repository.CommentRepository;
import com.project.lango.repository.QuizRepository;
import com.project.lango.repository.UserRepository;
import com.project.lango.service.CommentService;
import com.project.lango.service.QuizService;
import com.project.lango.ws.rest.Create.CreateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@RestController
@RequestMapping(value = "/quiz")
@CrossOrigin(origins = "http://localhost:8000")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

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
    @CrossOrigin(origins = "http://localhost:8000")
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
    @CrossOrigin(origins = "http://localhost:8000")
    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8000")
    public Quiz getByType(@PathVariable String type){

        Quiz.Type quizType;

        quizType = Quiz.Type.valueOf(type.toUpperCase());
        return quizRepository.getByType(quizType);
    }

    @RequestMapping(value = "/getQuizzes", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8000")
    public Map<Long, String> getQuizzes(){
        List<Quiz> quizzes = quizRepository.findAll();
        Map<Long, String> result = new HashMap<>();
        for(Quiz q : quizzes) {
            Long id = q.getId();
            String category = q.getType().toString();
            result.put(id, category);
        }
        return result;
    }

    @RequestMapping(value = "/addComment/{quizId}", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:8000")
    public ResponseEntity<?> addComment(@RequestBody @Valid CreateComment createComment, @PathVariable Long quizId){

        if(!quizRepository.exists(quizId)){
            return ResponseEntity.badRequest().body(new ObjectError("comment.quiz", "Invalid quiz ID."));
        }

        User user = userRepository.findByUsername(createComment.getUsername());
        if(user == null){
            return ResponseEntity.badRequest().body(new ObjectError("comment.user", "Invalid username."));
        }

        try{
            Comment comment = new Comment();
            comment.setUser(userRepository.findByUsername(createComment.getUsername()));
            comment.setQuiz(quizRepository.findOne(quizId));
            comment.setText(createComment.getText());
            commentService.addComment(comment);
            URI location = URI.create("/quiz/addComment/" + quizId);
            return ResponseEntity.created(location).build();
        } catch (UnsupportedOperationException exception){
            return ResponseEntity.badRequest().body(new ObjectError("quiz.id", exception.getMessage()));
        }
    }

    @RequestMapping(value = "/deleteComment/{commentId}", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "http://localhost:8000")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        if(!commentRepository.exists(commentId)){
            return ResponseEntity.badRequest().body(new ObjectError("question.id", "Comentariul cu acest id nu exista!"));
        }else{
            commentService.deleteComment(commentId);
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/allComments/{quizId}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8000")
    public List<Comment> getAllComments(@PathVariable Long quizId){
        return commentService.getAllComments(quizId);
    }
}
