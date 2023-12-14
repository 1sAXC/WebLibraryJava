package com.webJava.library.service.impl;

import com.webJava.library.repository.StatusRepository;
import com.webJava.library.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }
    @Override
    public void addVisitor() {
        var status = statusRepository.getReferenceById(1);
        status.setCount(status.getCount() + 1);
        statusRepository.save(status);
    }

    @Override
    public int getVisitors() {
        return statusRepository.getReferenceById(1).getCount();
    }
}
