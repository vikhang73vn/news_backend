package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.SearchKeywordDto;
import com.erp.backend.entities.SearchKeyword;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class SearchKeywordDTOMapper implements Function<SearchKeyword, SearchKeywordDto> {
    @Override
    public SearchKeywordDto apply(SearchKeyword searchKeyword) {
        return new SearchKeywordDto(searchKeyword.getId(),searchKeyword.getKeyword());
    }
}
