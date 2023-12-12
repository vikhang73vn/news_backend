package com.erp.backend.repositories;

import com.erp.backend.entities.Author;
import com.erp.backend.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    Optional<Publication> findByNameIgnoreCase(String name);
}
