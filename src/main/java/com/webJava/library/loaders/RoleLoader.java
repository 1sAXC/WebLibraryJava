package com.webJava.library.loaders;

import com.webJava.library.models.Role;
import com.webJava.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleLoader(final RoleRepository countryRepository) {
        this.roleRepository = countryRepository;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (roleRepository.count() != 0) return;

        final Role user = new Role("default");
        final Role moderator = new Role("moderator");
        final Role admin = new Role("admin");

        roleRepository.save(user);
        roleRepository.save(moderator);
        roleRepository.save(admin);
    }
}
