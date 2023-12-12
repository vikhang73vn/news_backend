package com.erp.backend.dtos.mappers;

import com.erp.backend.dtos.PublicationDto;
import com.erp.backend.entities.Publication;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class PublicationDTOMapper implements Function<Publication, PublicationDto> {

    @Override
    public PublicationDto apply(Publication publication) {
        return new PublicationDto(publication.getId(), publication.getName());
    }
}
