package com.project.lango.ws.rest;

import com.project.lango.domain.User;
import com.project.lango.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by ioana on 8/05/2017.
 */
@RestController
@RequestMapping(value = "/home")
@CrossOrigin(origins = "http://localhost:8000")
public class HomeController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/user")
    public User user(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepository.findByUsername(loggedUsername);
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password,
                                                     HttpServletResponse response) throws IOException {

        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
