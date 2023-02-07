package com.ca.controller;

import com.ca.entity.Document;
import com.ca.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam Long customerId,@RequestParam Long customerUserId,
                                         @RequestParam MultipartFile file){
        System.out.println("Inside Upload controller");

        return ResponseEntity.ok(documentService.uploadDocument(customerId,customerUserId,file));
    }

    @GetMapping("/doc")
    public List<Document> getDocument(@RequestParam Long customerId){

        return documentService.getDocument(customerId);
    }

}
