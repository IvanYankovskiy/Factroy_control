package com.factory.control.service;

import com.factory.control.repository.ExtruderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtruderService {

    @Autowired
    private ExtruderRepository repository;

}
