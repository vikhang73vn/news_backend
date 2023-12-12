package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.*;
import com.erp.backend.entities.Article;
import com.erp.backend.entities.RelatedArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;
@Service
public class RelatedArticleDTOMapper implements Function<Article, RelatedArticleDto> {
    @Autowired
    CategoryDTOMapper categoryDTOMapper;
    @Autowired
    AuthorDtoMapper authorDtoMapper;
    @Autowired
    PublicationDTOMapper publicationDTOMapper;
    @Autowired
    SearchKeywordDTOMapper searchKeywordDTOMapper;
    @Autowired
    UserDTOMapper userDTOMapper;

    @Override
    public RelatedArticleDto apply(Article relatedArticle) {
        CategoryDto categoryDto=categoryDTOMapper.apply(relatedArticle.getCategory());
        AuthorDto authorDto = authorDtoMapper.apply(relatedArticle.getAuthor());
        PublicationDto publicationDto = publicationDTOMapper.apply(relatedArticle.getPublication());
        List<SearchKeywordDto> listSearchKeywordDto = Optional.ofNullable(relatedArticle.getSearchKeywords())
                .orElse(Collections.emptyList())
                .stream()
                .map(searchKeywordDTOMapper::apply)
                .collect(Collectors.toList());
                //relatedArticle.getRelatedArticle().getSearchKeywords().stream().map(searchKeywordDTOMapper::apply).collect(Collectors.toList());
        UserDto userDto = userDTOMapper.apply(relatedArticle.getUserCreate());
        return new RelatedArticleDto(relatedArticle.getId(),relatedArticle.getTitle(),categoryDto,authorDto,publicationDto,listSearchKeywordDto,relatedArticle.getUrl(),userDto);
    }
}
