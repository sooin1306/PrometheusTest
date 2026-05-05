package com.example.prometheustest.controller;

import com.example.prometheustest.entity.Post;
import com.example.prometheustest.entity.User;
import com.example.prometheustest.service.PostService;
import com.example.prometheustest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        model.addAttribute("post", post);
        return "posts/view";
    }

    @GetMapping("/new")
    public String createForm() {
        return "posts/form";
    }

    @PostMapping
    public String create(@RequestParam String title,
                        @RequestParam String content,
                        Authentication authentication) {
        User author = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        postService.create(title, content, author);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, Authentication authentication) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getUsername().equals(authentication.getName())) {
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                        @RequestParam String title,
                        @RequestParam String content,
                        Authentication authentication) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getUsername().equals(authentication.getName())) {
            return "redirect:/posts";
        }

        postService.update(id, title, content);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication authentication) {
        Post post = postService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getUsername().equals(authentication.getName())) {
            return "redirect:/posts";
        }

        postService.delete(id);
        return "redirect:/posts";
    }
}
