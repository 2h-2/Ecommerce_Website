package com.ecomerce.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.backend.model.dto.UserDto;
import com.ecomerce.backend.model.entity.User;
import com.ecomerce.backend.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo ;

    public UserDto getUser(int id){
        Optional<User> user =  userRepo.findById(id);
        if(user.isPresent()){
            return UserDto.toDto(user.get());
        }
        return null;
    }

    public List<UserDto> getAllUsers(){
        List<User> users =  userRepo.findAll();
        
        if(!users.isEmpty()){
            List<UserDto> usersDto = new ArrayList<UserDto>();
            users.stream().forEach(user -> usersDto.add(UserDto.toDto(user)) );
            return usersDto;
        }
        return null;
    }

    public List<User> test(){
        List<User> users =  userRepo.findAll();
        
        return users;
    }

    public UserDto save(UserDto user){
        return UserDto.toDto(userRepo.save(User.toEntity(user)));
    }

    public UserDto update(UserDto user){
        return UserDto.toDto(userRepo.save(User.toEntity(user)));
    }

    public void delete(int id){
        userRepo.deleteById(id);
    }
}

