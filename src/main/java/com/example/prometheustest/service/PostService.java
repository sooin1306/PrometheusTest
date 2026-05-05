package com.example.prometheustest.service;

import com.example.prometheustest.entity.Post;
import com.example.prometheustest.entity.User;
import com.example.prometheustest.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Post> findById(Long id) {
        Post post = postRepository.findOne(id);
        return Optional.ofNullable(post);
    }

    public Post create(String title, String content, User author) {
        Post post = new Post(title, content, author);
        return postRepository.save(post);
    }

    public Post update(Long id, String title, String content) {
        Post post = postRepository.findOne(id);
        if (post == null) {
            throw new IllegalArgumentException("Post not found");
        }
        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.delete(id);
    }
}
