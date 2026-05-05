package com.example.prometheustest.config;

import com.example.prometheustest.entity.Post;
import com.example.prometheustest.entity.User;
import com.example.prometheustest.repository.PostRepository;
import com.example.prometheustest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create test user
        User testUser = new User("admin", passwordEncoder.encode("admin"));
        testUser.setRole("ROLE_ADMIN");
        userRepository.save(testUser);

        User normalUser = new User("user", passwordEncoder.encode("user"));
        userRepository.save(normalUser);

        // Create sample posts
        Post post1 = new Post("Welcome to the Board", "This is the first post on our board application.", testUser);
        postRepository.save(post1);

        Post post2 = new Post("Sample Post", "This is a sample post content for testing purposes.", normalUser);
        postRepository.save(post2);

        System.out.println("===========================================");
        System.out.println("Test users created:");
        System.out.println("  - admin / admin (ROLE_ADMIN)");
        System.out.println("  - user / user (ROLE_USER)");
        System.out.println("===========================================");
    }
}
