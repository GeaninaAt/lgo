package com.project.lango.ws.rest;

import com.project.lango.domain.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by ioana on 8/05/2017.
 */
@RestController
@RequestMapping(value = "/questions")
public class QuestionController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> addQuestion(@RequestBody @Valid Question question){

    }
}
