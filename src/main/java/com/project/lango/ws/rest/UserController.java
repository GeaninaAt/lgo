package com.project.lango.ws.rest;

import com.project.lango.domain.User;
import com.project.lango.repository.UserRepository;
import com.project.lango.service.UserService;
import com.project.lango.ws.rest.Create.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandra on 4/29/2017.
 */
@RestController
@RequestMapping(value = "/users")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postInit(){
        if(userRepository.findByUsername("admin") == null){

            final User defaultAdmin = new User();
            defaultAdmin.setFname("admin");
            defaultAdmin.setLname("admin");
            defaultAdmin.setEmail("admin@admin.admin");
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword("admin");

            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            defaultAdmin.setRoles(roles);

            userService.addUser(defaultAdmin);
        } else {
            System.out.println("Default admin added");
        }
    }

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreateUser createUser){
        User user = new User();
        user.setFname(createUser.getFname());
        user.setLname(createUser.getLname());
        user.setBirthdate(createUser.getBirthdate());
        user.setCountry(createUser.getCountry());
        user.setEmail(createUser.getEmail());
        user.setPassword(createUser.getPassword());
        user.setPhone(createUser.getPhone());
        user.setUsername(createUser.getUsername());
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);


        try { userService.addUser(user);
            URI location=URI.create("/users/createAccount");
            return ResponseEntity.created(location).build();
        } catch ( UnsupportedOperationException e){
                return ResponseEntity.badRequest().body(new ObjectError("user.id", "Ba esti prost"));
        }

        }


}
