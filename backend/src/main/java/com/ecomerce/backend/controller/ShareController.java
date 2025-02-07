package com.ecomerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.backend.model.entity.Category;
import com.ecomerce.backend.model.entity.Color;
import com.ecomerce.backend.model.entity.Size;
import com.ecomerce.backend.service.CategoryService;
import com.ecomerce.backend.service.ColorServise;
import com.ecomerce.backend.service.SizeService;

@RestController
@RequestMapping("/api")
public class ShareController {
    @Autowired
    ColorServise colorServise;

    @Autowired
    SizeService sizeServise;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/colors")
    public ResponseEntity<List<Color>> getAllColors(){
        return new ResponseEntity<>(colorServise.getAll(), HttpStatus.OK);
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<Size>> getAllSizes(){
        return new ResponseEntity<>(sizeServise.getAll(), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

}
