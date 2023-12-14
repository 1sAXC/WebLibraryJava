package com.webJava.library.helpers.impl;

import org.springframework.stereotype.Service;

@Service
public class VisitCounterService {

    private int visitCount = 0;

    public int getVisitCount() {
        return visitCount;
    }

    public void incrementCount() {
        visitCount++;
    }
}
