package com.erp.backend.dtos;

import com.erp.backend.entities.Comment;
import com.erp.backend.entities.RelatedArticle;
import com.erp.backend.entities.SearchKeyword;

import java.util.List;

public record ArticleDto(Long articleId , String articleTitle , String articleContent , CategoryDto category, AuthorDto author, String url, PublicationDto articlePublication, List<Comment> listComment, List<SearchKeywordDto> listSearchKeyword , List<RelatedArticleDto> listRelatedArticle,UserDto userCreate) {
}
