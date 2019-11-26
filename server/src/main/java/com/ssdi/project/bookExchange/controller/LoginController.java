package com.ssdi.project.bookExchange.controller;

import com.ssdi.project.bookExchange.model.User;
import com.ssdi.project.bookExchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public HashMap<String, Object> createNewUser(@RequestBody User user, BindingResult bindingResult) {
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
            User saveUser = userService.saveUser(user);
            hm.put("message","User has been registered successfully");
            hm.put("user",saveUser);
            hm.put("status", String.valueOf(200));
        }
        return hm;
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public HashMap<String, Object> home(HttpServletRequest request){
        HashMap<String,Object> hm = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        User user = userService.findUserByEmail(auth.getName());
        hm.put("message","Logged in successfully");
        hm.put("user",user);
        hm.put("status", String.valueOf(200));
        return hm;
    }

    @GetMapping("/users")
    public List<User> findAllUsers(){
        List<User> listUsers = userService.listAll();
        return listUsers;
    }

    @GetMapping("/userById/{id}")
    public User getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return user;
    }

    @GetMapping("user/search")
    @ResponseBody
    public List<User> userSearch(@RequestParam String query) {
        List<User> userList = new ArrayList<>();
        if(query != null && !query.isEmpty()) {
            userList = userService.searchUsers(query);
        }
        return userList;
    }


}
