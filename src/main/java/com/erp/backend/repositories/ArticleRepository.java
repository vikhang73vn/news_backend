package com.erp.backend.repositories;

import com.erp.backend.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findFirst10ByOrderByCreatedAtDesc();
    List<Article> findByCategoryNameIgnoreCase(String categoryName);
    List<Article> findByCategoryId(Long categoryId);
    List<Article> findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrPublicationNameContainingIgnoreCaseOrSearchKeywordsKeywordContainingIgnoreCase(String title, String authorName, String publicationName, String keyword);
//List<Article> findAllByCategoryId(Long id);
List<Article> findBySearchKeywords_Id(Long id);
List<Article> findBySearchKeywords_Keyword(String keyword);
Page<Article> findAll(Pageable pageable);



}
