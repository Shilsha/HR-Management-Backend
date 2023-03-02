package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Contact;
import com.ca.service.ContactService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/add_contact")
    public ResponseEntity addContact(@RequestBody Contact contact) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, contactService.addContact(contact), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_contact_by_contactId")
    public ResponseEntity getContactById(@RequestParam Long contactId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, contactService.getContactById(contactId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_contact")
    public ResponseEntity updateContact(@RequestBody Contact contact) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, contactService.updateContact(contact), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping("/delete_contact")
    public ResponseEntity deleteContact(@RequestParam Long contactId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, contactService.deleteContact(contactId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

}
