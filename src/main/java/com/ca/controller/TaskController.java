package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Task;
import com.ca.service.TaskService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add_task")
    public ResponseEntity addTask(@RequestBody Task task) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,taskService.addTask(task), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_task_by_id")
    public ResponseEntity getTaskById(@RequestParam Long taskId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,taskService.getTaskById(taskId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping("/delete_task")
    public ResponseEntity deleteTask(@RequestParam Long taskId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,taskService.deleteTask(taskId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_task")
    public ResponseEntity updateTask(@RequestBody Task task) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,taskService.updateTask(task), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/upload_document")
    public ResponseEntity uploadTaskDocument(@RequestParam Long taskId, @RequestParam MultipartFile file) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,taskService.uploadTaskDocument(taskId, file), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
}
