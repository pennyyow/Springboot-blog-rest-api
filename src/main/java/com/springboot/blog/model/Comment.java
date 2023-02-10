package com.springboot.blog.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(
          strategy = GenerationType.IDENTITY
  )
  private long id;

  @Column(name="name", nullable = false)
  private String name;

  @Column(name="email", nullable = false)
  private String email;

  @Column(name="body", nullable = false)
  private String body;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;
}
