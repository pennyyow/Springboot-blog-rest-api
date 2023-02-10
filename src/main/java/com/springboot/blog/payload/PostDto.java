package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
  private long id;

  @NotEmpty
  @Size(min = 1, message = "Post title should have at least 1 character")
  private String title;

  @NotEmpty
  @Size(min = 10, message = "Post description should have at least 10 characters")
  private String description;

  @NotEmpty
  private String content;
  private Set<CommentDto> comments;
}
