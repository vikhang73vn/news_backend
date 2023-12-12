package com.erp.backend.repositories;

import com.erp.backend.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    public Author getAuthorById(long id);
    Optional<Author> findByNameIgnoreCase(String name);
    public List<Author> findAllByOrderByNameAsc();
}
