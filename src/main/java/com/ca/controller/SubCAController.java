package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.SubCA;
import com.ca.model.response.SubCAResponseDto;
import com.ca.model.response.SubCAServiceResponseDto;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.SubCARepository;
import com.ca.service.SubCAService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub_ca")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubCAController {

    @Autowired
    private SubCAService subCAService;
    @Autowired
    private SubCARepository subCARepository;

    @PostMapping("create_subCA")
    public ResponseEntity create(@RequestBody SubCA subCA) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,subCAService.create(subCA), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_all_subCA")
    public ResponseEntity getAllSubCA(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,subCAService.getAllSubCA(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_subCA_by_subCAId")
    public ResponseEntity getSingleSubCA(@RequestParam Long id) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,subCAService.getSingleSubCA(id), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_subCA_by_CAId")
    public ResponseEntity getSubCAByCAId(@RequestParam Long caId,
                                         @RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,subCAService.getSubCAByCAId(caId,pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_by_subCaId")
    public ResponseEntity getServices(@RequestParam Long subCaId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,subCAService.getServices(subCaId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_subCA")
    public void updateSubCA(@RequestBody SubCA subCA ,@PathVariable Long id){
        subCAService.updateSubCA(subCA,id);
    }

    @DeleteMapping("/delete_by_subCaId")
    public void deleteSubCA(@PathVariable Long id){
        subCAService.deleteSubCA(id);
    }
}
