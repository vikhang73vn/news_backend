package com.erp.backend.dtos;

import com.erp.backend.entities.Comment;

import java.util.List;


    public record ArticleSearchDto(Long articleId , String articleTitle , String articleContent , CategoryDto category, AuthorDto author, String url, PublicationDto articlePublication , UserDto userCreate) {
    }


