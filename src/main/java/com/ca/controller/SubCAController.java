package com.ca.controller;

import com.ca.entity.SubCA;
import com.ca.model.response.SubCAResponseDto;
import com.ca.model.response.SubCAServiceResponseDto;
import com.ca.repository.SubCARepository;
import com.ca.service.SubCAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subca")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubCAController {

    @Autowired
    private SubCAService subCAService;
    @Autowired
    private SubCARepository subCARepository;

    @PostMapping
    public SubCA create(@RequestBody SubCA subCA){
        return subCAService.create(subCA);
    }

    @GetMapping
    public List<SubCA> getAllSubCA(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return subCAService.getAllSubCA(pageNumber, pageSize);
    }

    @GetMapping("/id")
    public SubCA getSingleSubCA(@RequestParam Long id){
        return subCAService.getSingleSubCA(id);
    }

    @GetMapping("/c")
    public List<SubCAResponseDto> getSubCAByCAId(@RequestParam Long caId){
        return subCAService.getSubCAByCAId(caId);
    }

    @GetMapping("/service")
    public List<SubCAServiceResponseDto> getServices(@RequestParam Long subCaId){
        return subCAService.getServices(subCaId);
    }

    @PutMapping("/{id}")
    public void updateSubCA(@RequestBody SubCA subCA ,@PathVariable Long id){
        subCAService.updateSubCA(subCA,id);
    }

    @DeleteMapping("/{id}")
    public void deleteSubCA(@PathVariable Long id){
        subCAService.deleteSubCA(id);
    }
}
