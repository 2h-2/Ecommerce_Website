package com.ecomerce.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.backend.model.entity.Size;
import com.ecomerce.backend.repository.SizeRepo;

@Service
public class SizeService {
    @Autowired
    private SizeRepo sizeRepo;

    public List<Size> getAll(){
        return sizeRepo.findAll();
    }
}
