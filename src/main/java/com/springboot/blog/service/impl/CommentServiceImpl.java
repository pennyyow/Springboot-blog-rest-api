package com.springboot.blog.service.impl;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Comment;
import com.springboot.blog.model.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  private final ModelMapper mapper;

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.mapper = mapper;
  }

  @Override
  public CommentDto createComment(long postId, CommentDto commentDto) {
    // retrieve first the post for comment
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = mapToEntity(commentDto);

    // set post to comment
    comment.setPost(post);

    // save comment to DB
    Comment newComment = commentRepository.save(comment);

    return mapToDTO(newComment);
  }

  @Override
  public List<CommentDto> getCommentsByPostId(long postId) {
    // retrieve comments by postId
    List<Comment> comments = commentRepository.findByPostId(postId);

    // convert list of comment entities to comment dto for security
    return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Override
  public CommentDto getCommentById(long id, long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(id).orElseThrow(() ->
    new ResourceNotFoundException("Comment", "id", id));

    if(!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    return mapToDTO(comment);
  }

  @Override
  public CommentDto updateCommentById(CommentDto commentRequest, long id, long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    Comment comment = commentRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Comment", "id", id));

    if(!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    comment.setBody(commentRequest.getBody());
    Comment updatedComment = commentRepository.save(comment);

    return mapToDTO(updatedComment);
  }

  @Override
  public void deleteComment(long id, long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    Comment comment = commentRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Comment", "id", id));

    if(!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    commentRepository.delete(comment);
  }

  private Comment mapToEntity(CommentDto commentDto) {
//    Comment comment = new Comment();
//    comment.setId(commentDto.getId());
//    comment.setName(commentDto.getName());
//    comment.setEmail(commentDto.getEmail());
//    comment.setBody(commentDto.getBody());

    return mapper.map(commentDto, Comment.class);
  }

  private CommentDto mapToDTO(Comment comment) {
//    CommentDto commentDto = new CommentDto();
//    commentDto.setId(comment.getId());
//    commentDto.setName(comment.getName());
//    commentDto.setEmail(comment.getEmail());
//    commentDto.setBody(comment.getBody());

    return mapper.map(comment, CommentDto.class);
  }
}
