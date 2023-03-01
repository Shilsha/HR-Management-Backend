package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Request;
import com.ca.service.RequestService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/add_request")
    public ResponseEntity addRequest(@RequestBody Request request) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,requestService.addRequest(request), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_request_by_caId")
    public ResponseEntity getRequestByCAId(@RequestParam Long caId, @RequestParam Integer pageNumber,@RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,requestService.getRequestByCAId(caId,pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_request_by_customerId")
    public ResponseEntity getRequestByCustomerId(@RequestParam Long customerId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,requestService.getRequestByCustomerId(customerId, pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/resolved_by_id")
    public ResponseEntity resolved(@RequestParam Long requestId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,requestService.resolved(requestId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping("/delete_request")
    public ResponseEntity deleteRequest(Long requestId) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,requestService.deleteRequest(requestId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
}
