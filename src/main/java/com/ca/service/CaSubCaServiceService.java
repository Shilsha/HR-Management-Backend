package com.ca.service;

import com.ca.entity.CaSubCaService;
import com.ca.repository.CaSubCaServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaSubCaServiceService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CaSubCaServiceRepository caSubCaServiceRepository;

    public List<CaSubCaService> getAllServices() {
        List<CaSubCaService> services = caSubCaServiceRepository.findAll();
        return services;
    }

    public CaSubCaService addService(CaSubCaService service) {
        logger.info("Add service successfully {}",service.getId());
        return caSubCaServiceRepository.save(service);
    }
}
