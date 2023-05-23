package com.erp.backend.controllers;

import com.erp.backend.dtos.DemoDto;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

  @PostMapping
  public ResponseEntity<String> sayHello(@RequestBody @Valid DemoDto demoDto) {
    return ResponseEntity.ok("Hello from secured endpoint");
  }

}
