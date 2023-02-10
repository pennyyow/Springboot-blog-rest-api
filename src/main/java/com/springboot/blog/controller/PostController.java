package com.springboot.blog.controller;

import com.springboot.blog.model.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  // Create blog posts rest api
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
  }

  // Get all posts rest api
  @GetMapping
  public PostResponse getAllPosts(
          @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
  ) {
    return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
  }

  // Get post by id rest api
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable("id")  long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PostMapping("/{id}/update")
  public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id) {
    return ResponseEntity.ok(postService.updatePost(postDto, id));
  }

  @PostMapping("/{id}/delete")
  public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
    postService.deletePost(id);

    return ResponseEntity.ok("Post deleted successfully!");
  }
}
