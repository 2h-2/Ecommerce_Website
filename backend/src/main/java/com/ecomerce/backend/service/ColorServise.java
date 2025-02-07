package com.ecomerce.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.backend.model.entity.Color;
import com.ecomerce.backend.repository.ColorRepo;

@Service
public class ColorServise {
    @Autowired
    private ColorRepo colorRepo;

    public List<Color> getAll(){
        return colorRepo.findAll();
    }
}
