package com.erp.backend.repositories;

import com.erp.backend.entities.Article;
import com.erp.backend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
   // void deleteByArticle(Article article);
}
