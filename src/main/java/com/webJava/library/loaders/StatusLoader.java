package com.webJava.library.loaders;

import com.webJava.library.models.Role;
import com.webJava.library.models.Status;
import com.webJava.library.repository.RoleRepository;
import com.webJava.library.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StatusLoader implements ApplicationRunner {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusLoader(final StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (statusRepository.count() != 0) return;

        statusRepository.save(new Status());
    }
}
