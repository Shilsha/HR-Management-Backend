package com.ca.controller;

import com.ca.entity.Document;
import com.ca.model.response.DocumentResponseDto;
import com.ca.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam Long userId,
                                         @RequestParam MultipartFile file){

        return ResponseEntity.ok(documentService.uploadDocument(userId,file));
    }

    @GetMapping
    public List<Document> getDocument(@RequestParam Long userId,@RequestParam Integer pageNumber, @RequestParam Integer pageSize){

        return documentService.getDocument(userId, pageNumber, pageSize);
    }

    @GetMapping("/search")
    public List<DocumentResponseDto> searchDocument(@RequestParam String docName){
        return documentService.searchDocument(docName);
    }

}
