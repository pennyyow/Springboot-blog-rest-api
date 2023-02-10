package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  // create comment rest api
  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@Valid @PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @GetMapping("/posts/{postId}/comments")
  public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId) {
    return commentService.getCommentsByPostId(postId);
  }

  @GetMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") long id, @PathVariable("postId") long postId) {
    return new ResponseEntity<>(commentService.getCommentById(id, postId), HttpStatus.OK);
  }

  @PostMapping("/posts/{postId}/comments/{id}/update")
  public ResponseEntity<CommentDto> updateCommentById(
          @Valid
          @RequestBody CommentDto commentRequest,
          @PathVariable("id") long id,
          @PathVariable("postId") long postId
  ) {
    return new ResponseEntity<>(commentService.updateCommentById(commentRequest, id, postId), HttpStatus.OK);
  }

  @PostMapping("/posts/{postId}/comments/{id}/delete")
  public ResponseEntity<String> deleteComment(@PathVariable("id") long id, @PathVariable("postId") long postId) {
    commentService.deleteComment(id, postId);
    return ResponseEntity.ok("Comment successfully deleted!");
  }
}
