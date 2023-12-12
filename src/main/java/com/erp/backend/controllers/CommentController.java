package com.erp.backend.controllers;

import com.erp.backend.dtos.request.CommentRequest;
import com.erp.backend.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    CommentService service;
    @PostMapping(value = {"/comment/create"})
    public ResponseEntity<?> createComment(@RequestAttribute("email") String email,@Valid @RequestBody CommentRequest request){
        return  ResponseEntity.ok(service.createComment(email,request));
    }

}
