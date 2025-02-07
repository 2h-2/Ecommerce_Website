package com.ecomerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.error.ResourceNotFoundException;
import com.ecomerce.backend.model.dto.UserDto;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.service.UserService;

@CrossOrigin(origins = "http://localhost:4200") 

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id){
        UserDto user = userService.getUser(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found for id: " + id);
        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public List<User> test(){
        return userService.test();
    }

    @PostMapping("/addUser")
    public UserDto addUser(@RequestBody UserDto user){
        return userService.save(user);
    }

    @PostMapping("/updateUser")
    public UserDto updateUser(@RequestBody UserDto user){
        return userService.update(user);
    }

    @DeleteMapping("/delUser")
    public void deletUser(@RequestParam int id){
        userService.delete(id);
    }

}
