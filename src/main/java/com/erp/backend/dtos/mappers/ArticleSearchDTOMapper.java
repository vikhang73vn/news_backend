package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.*;
import com.erp.backend.entities.Article;
import com.erp.backend.entities.SearchKeyword;
import com.erp.backend.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleSearchDTOMapper implements Function<Article, ArticleSearchDto> {
    @Autowired
    AuthorDtoMapper authorDtoMapper;
    @Autowired
    CategoryDTOMapper categoryDTOMapper;
    @Autowired
    PublicationDTOMapper publicationDTOMapper;
    @Autowired
    SearchKeywordDTOMapper searchKeywordDTOMapper;
    @Autowired
    UserDTOMapper userDTOMapper;
    @Autowired
    RelatedArticleDTOMapper relatedArticleDTOMapper;
    @Autowired
    ArticleRepository articleRepository;


    @Override
    public ArticleSearchDto apply(Article article) {

        AuthorDto authorDto = authorDtoMapper.apply(article.getAuthor());
        CategoryDto categoryDto = categoryDTOMapper.apply(article.getCategory());
        PublicationDto publicationDto= publicationDTOMapper.apply(article.getPublication());
        List<SearchKeywordDto> listSearchKeywordDto = Optional.ofNullable(article.getSearchKeywords())
                .orElse(Collections.emptyList())
                .stream()
                .map(searchKeywordDTOMapper::apply)
                .collect(Collectors.toList());

        //article.getSearchKeywords().stream().map(searchKeywordDTOMapper::apply).collect(Collectors.toList());
        UserDto userDto = userDTOMapper.apply(article.getUserCreate());
        List<Article> articleList = articleRepository.findByCategoryId(article.getCategory().getId());
        List<Article> articles;
        if (articleList.size() > 6) {
            Collections.shuffle(articleList);
            articles = articleList.subList(0, 5);
        } else {
            articles = articleList;
        }
        List<RelatedArticleDto> listRelatedArticleDtos = Optional.ofNullable(articles)
                .orElse(Collections.emptyList())
                .stream()
                .map(relatedArticleDTOMapper::apply)
                .collect(Collectors.toList());

        return new ArticleSearchDto(article.getId(),article.getTitle(),article.getContent(),categoryDto,authorDto,article.getUrl(),publicationDto,userDto);
    }
}
