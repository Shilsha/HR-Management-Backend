package com.ca.controller;

import com.ca.entity.CA;
import com.ca.model.response.CaServiceResponseDto;
import com.ca.service.CAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca")
public class CAController {

    @Autowired
    private CAService caService;

    @PostMapping
    public CA createCA(@RequestBody CA ca){
        return caService.createCA(ca);
    }

    @GetMapping
    public List<CA> getAllCA(){
        return caService.getAllCA();
    }

    @GetMapping("/service")
    public List<CaServiceResponseDto> getService(@RequestParam Long caId){
        return caService.getService(caId);
    }

    @PutMapping("/{caID}")
    public void updateCA(@RequestBody CA ca, @PathVariable Long caId){
        caService.updateCA(ca,caId);
    }

    @DeleteMapping("/{caID}")
    public void deleteCA(@PathVariable Long caId){
        caService.deleteCA(caId);
    }
}
