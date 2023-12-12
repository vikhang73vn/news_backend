package com.erp.backend.dtos;

import com.erp.backend.entities.Category;

import java.util.List;

public record RelatedArticleDto(Long idRelatedArticle, String titleRelatedArticle, CategoryDto categoryRelatedArticle, AuthorDto authorRelatedArticle, PublicationDto publicationRelatedArticle ,
                                List<SearchKeywordDto> listSearchKeywordRelatedArticle ,String url, UserDto userCreateRelatedArticle) {
}
