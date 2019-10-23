package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public HashMap<String,Object> login(){
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("message","Invalid email id or password!");
        hm.put("error","Failed to login user");
        hm.put("status", String.valueOf(500));
        return hm;
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object> validateLogin(@RequestBody String email, @RequestBody String password) {
        HashMap<String,Object> hm = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return hm;
    }*/

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HashMap<String, Object> createNewUser(@Valid User user, BindingResult bindingResult) {
        HashMap<String,Object> hm = new HashMap<>();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            List<ObjectError> al = bindingResult.getAllErrors();
            List<String> errorMessage = new ArrayList<>();
            for (ObjectError objectError: al){
                errorMessage.add(objectError.getDefaultMessage());
            }
            hm.put("message","Failed to register user");
            hm.put("error",errorMessage);
            hm.put("status", String.valueOf(500));
        } else {
            userService.saveUser(user);
            hm.put("message","User has been registered successfully");
            hm.put("user",user);
            hm.put("status", String.valueOf(200));
        }
        return hm;
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public HashMap<String, Object> home(){
        HashMap<String,Object> hm = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        hm.put("message","Logged in successfully");
        hm.put("user",user);
        hm.put("status", String.valueOf(200));
        return hm;
    }


}
