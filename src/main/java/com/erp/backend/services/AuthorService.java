package com.erp.backend.services;

import com.erp.backend.dtos.AuthorDto;
import com.erp.backend.dtos.mappers.AuthorDtoMapper;
import com.erp.backend.dtos.request.AuthorRequest;
import com.erp.backend.dtos.request.UpdateAuthorRequest;
import com.erp.backend.entities.Author;
import com.erp.backend.exceptions.ResourceNotFoundException;
import com.erp.backend.models.Response;
import com.erp.backend.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository repository ;
    @Autowired
    AuthorDtoMapper mapper;

public AuthorDto createAuthor(AuthorRequest request){
    Author author= Author.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
Author save= repository.save(author);
return mapper.apply(save);

}
    public AuthorDto updateAuthor(UpdateAuthorRequest request){
        Optional<Author> optionalAuthor=repository.findById(request.getId());
        if(!optionalAuthor.isPresent()){throw new ResourceNotFoundException("id Author does not exist");
        }
        Author author=optionalAuthor.get();
        if (request.getName()!=null){

            author.setName(request.getName());
        }
        if (request.getDescription()!=null){
            author.setDescription(request.getDescription());
        }

        Author save=repository.save(author);
        return mapper.apply(save);
    }
    public Response deleteAuthor (Long idAuthor){
        Optional<Author> optionalAuthor=repository.findById(idAuthor);
        Author author=optionalAuthor.get();
        repository.delete(author);
        return new Response(200,null,null);
    }
    public List<AuthorDto> getAll(){
        List<Author> list=repository.findAll();
        List<AuthorDto> listResult=list.stream().map(mapper::apply).collect(Collectors.toList());
        return listResult;
    }
    public AuthorDto getAuthor(Long idAuthor){
return mapper.apply( repository.findById(idAuthor).get());
    }
    public AuthorDto getAuthor(String nameAuthor){
        return mapper.apply( repository.findByNameIgnoreCase(nameAuthor).get());
    }




}
