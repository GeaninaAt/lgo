package com.project.lango.service;

import com.project.lango.domain.User;
import com.project.lango.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alexandra on 4/29/2017.
 */
@Service
@Transactional
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        User createdUser =  userRepository.save(user);
        return createdUser;
    }
}
