package com.ca.service;

import com.ca.entity.Service;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.repository.CaServiceRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CaServicesService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CaServiceRepository caSubCaServiceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Service> getAllServices(Integer pageNumber, Integer pageSize) {
        logger.info("Getting all services");
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Service> pageService = caSubCaServiceRepository.findAll(pageable);
        List<Service> services = pageService.getContent();
        return services;
    }

    public Service addService(Service service) {
        logger.info("Add service successfully {}",service.getId());
        Optional<User> user = userRepository.findById(service.getUserId());
        if (!user.isPresent()){
            throw new BadReqException("User Id not present in DB");
        }
        return caSubCaServiceRepository.save(service);
    }

    public List<Service> getService(Long userId, Integer pageNumber, Integer pageSize) {
        logger.info("Get service of user id {}",userId);
        List<Service> services = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            services = caSubCaServiceRepository.findByUserId(userId);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<Service> pageService = caSubCaServiceRepository.findByUserid(userId,pageable);
            services = pageService.getContent();
        }
        return services;
    }
}
