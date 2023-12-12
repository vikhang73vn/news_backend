package com.erp.backend.controllers;

import com.erp.backend.dtos.ArticleDto;
import com.erp.backend.dtos.SearchDto;
import com.erp.backend.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class SreachController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<?>> sreachArticle(@PathVariable(value = "keyword") String keyword){
        return ResponseEntity.ok(articleService.sreachArticle(keyword));
    }

}
