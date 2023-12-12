package com.erp.backend.repositories;

import com.erp.backend.entities.Author;
import com.erp.backend.entities.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword,Long> {
    Optional<SearchKeyword> findByKeyword(String name);
}
