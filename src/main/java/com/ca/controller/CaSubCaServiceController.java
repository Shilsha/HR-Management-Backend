package com.ca.controller;

import com.ca.entity.CaSubCaService;
import com.ca.service.CaSubCaServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CaSubCaServiceController {

    @Autowired
    private CaSubCaServiceService caSubCaServiceService;

    @GetMapping
    public List<CaSubCaService> getAllService(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return caSubCaServiceService.getAllServices(pageNumber, pageSize);
    }

    @GetMapping("id")
    public List<CaSubCaService> getService(@RequestParam Long userId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return caSubCaServiceService.getService(userId, pageNumber, pageSize);
    }

    @PostMapping
    public CaSubCaService addService(@RequestBody CaSubCaService service){
        return caSubCaServiceService.addService(service);
    }

}
