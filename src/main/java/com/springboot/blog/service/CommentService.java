package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
  CommentDto createComment(long postId, CommentDto commentDto);

  List<CommentDto> getCommentsByPostId(long postId);

  CommentDto getCommentById(long id, long postId);

  CommentDto updateCommentById(CommentDto commentRequest, long id, long postId);

  void deleteComment(long id, long postId);
}
