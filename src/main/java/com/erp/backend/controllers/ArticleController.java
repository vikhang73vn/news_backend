package com.erp.backend.controllers;

import com.erp.backend.dtos.request.ArticleRequest;
import com.erp.backend.dtos.request.UpdateArticleRequest;
import com.erp.backend.repositories.ArticleRepository;
import com.erp.backend.repositories.AuthorRepository;
import com.erp.backend.repositories.CategoryRepository;
import com.erp.backend.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ArticleService service;
   // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/article/create")
    public ResponseEntity<?> createArticle(@RequestAttribute("email") String email,@Valid @RequestBody ArticleRequest request){
        service.uploadArticle(email,request);
        return ResponseEntity.ok("ok");
     //   return  ResponseEntity.ok("ok");

    }
@DeleteMapping("/article/delete/{idArticle}")
public ResponseEntity<?> deleteArticle (@PathVariable(value = "idArticle") Long idArticle){
        return ResponseEntity.ok(service.deleteArticle(idArticle));
}
     @GetMapping("/article/get/{idArticle}")
     public  ResponseEntity<?> getArticle(@PathVariable(value ="idArticle" ) long idArticle){
        return  ResponseEntity.ok(service.getArticle(idArticle));
     }
     @GetMapping("/article/getAll")
    public ResponseEntity<?> getAllArticle(){
        return ResponseEntity.ok(service.getAll());
     }
     @GetMapping("/article/new")
    public  ResponseEntity<?> topNewArticle(){
        return ResponseEntity.ok(service.topArticle());
     }
     @PutMapping("/article/favorite/{idArticle}")
    public ResponseEntity<?> favoriteArticle(@RequestAttribute("email") String email ,@PathVariable(value = "idArticle") Long idArticle){
        return ResponseEntity.ok(service.favorite(email,idArticle));
     }
    @PutMapping("/article/unfavorite/{idArticle}")
    public ResponseEntity<?> unfavoriteArticle(@RequestAttribute("email") String email ,@PathVariable(value = "idArticle") Long idArticle){
        return ResponseEntity.ok(service.unfavorite(email,idArticle));
    }
    @GetMapping("/article/category/id/{idCategory}")
    public  ResponseEntity<?> articleCategoryId(@PathVariable(value = "idCategory") Long idCategory){
        return ResponseEntity.ok(service.findArticleByCategory(idCategory));
    }
    @GetMapping("/article/category/name/{nameCategory}")
    public  ResponseEntity<?> articleCategoryName(@PathVariable(value = "nameCategory") String nameCategory){
        return ResponseEntity.ok(service.findArticleByCategory(nameCategory));
    }
    @PatchMapping("/article/update")
    public ResponseEntity<?> articleUpdate(@Valid @RequestBody UpdateArticleRequest request){

        return ResponseEntity.ok(service.updateArticle(request));
    }
    @GetMapping("/article/keyword/id/{idKeyword}")
    public  ResponseEntity<?> articleKeywordId( @PathVariable(value = "idKeyword") Long idKeyword){
        return ResponseEntity.ok(service.getArticleByKeywordId(idKeyword));
    }
    @GetMapping("/article/keyword/name/{Keyword}")
    public  ResponseEntity<?> articleKeywordId( @PathVariable(value = "Keyword") String Keyword){
        return ResponseEntity.ok(service.getArticleByKeywordName(Keyword));
    }

    @GetMapping("/article/getAllPage")
    public ResponseEntity<?> getAllPageArticle(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(service.getAllArticle(page,size));
    }
}
