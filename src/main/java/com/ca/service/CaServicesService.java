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
    private CaServiceRepository caServiceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Service> getAllServices() {
        logger.info("Getting all services");
        List<Service> services = caServiceRepository.findAll();
        return services;
    }

    public Service addService(Service service) {
        logger.info("Add service successfully {}",service.getId());
        Optional<User> user = userRepository.findById(service.getUserId());
        if (!user.isPresent()){
            throw new BadReqException("User Id not present in DB");
        }
        service.setServiceStatus(true);
        return caServiceRepository.save(service);
    }

    public List<Service> getService(Long userId) {
        logger.info("Get service of user id {}",userId);

        List<Service> services = caServiceRepository.findByUserId(userId);
        return services;
    }

    public List<Service> getDistinctService(Long userId) {

        logger.info("Get Distinct service");
        return caServiceRepository.findDistinctService(userId);
    }

    public List<Service> getSubServiceByService(String serviceName, Long caId) {
        logger.info("Get SubService by Service name : {}",serviceName);
        return caServiceRepository.findSubServiceByServiceName(serviceName, caId);
    }

    public Service deleteService(Long serviceId) {
        logger.info("Delete service by service_id : {}",serviceId);
        Optional<Service> service = caServiceRepository.findById(serviceId);
        if (!service.isPresent()){
            throw new BadReqException("Service id not present in DB");
        }
        Service service1 = service.get();
        service1.setServiceStatus(false);
        return caServiceRepository.save(service1);
    }
}
