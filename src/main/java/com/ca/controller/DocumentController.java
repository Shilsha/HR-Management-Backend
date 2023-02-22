package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.service.DocumentService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload_document")
    public ResponseEntity uploadDocument(@RequestParam Long userId,
                                         @RequestParam("file") MultipartFile file, @RequestParam Long serviceId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,documentService.uploadDocument(userId,file, serviceId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);

    }

    @GetMapping("/get_document_by_userId")
    public ResponseEntity getDocument(@RequestParam Long userId,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,documentService.getDocument(userId, pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/search_document")
    public ResponseEntity searchDocument(@RequestParam String docName, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,documentService.searchDocument(docName,pageNumber,pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

}
