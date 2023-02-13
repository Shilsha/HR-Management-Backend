package com.ca.service;

import com.ca.entity.CaSubCaService;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.repository.CaSubCaServiceRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CaSubCaServiceService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CaSubCaServiceRepository caSubCaServiceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CaSubCaService> getAllServices(Integer pageNumber, Integer pageSize) {
        logger.info("Getting all services");
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<CaSubCaService> pageService = caSubCaServiceRepository.findAll(pageable);
        List<CaSubCaService> services = pageService.getContent();
        return services;
    }

    public CaSubCaService addService(CaSubCaService service) {
        logger.info("Add service successfully {}",service.getId());
        Optional<User> user = userRepository.findById(service.getUserId());
        if (!user.isPresent()){
            throw new BadReqException("User Id not present in DB");
        }
        return caSubCaServiceRepository.save(service);
    }

    public List<CaSubCaService> getService(Long userId, Integer pageNumber, Integer pageSize) {

        logger.info("Get service of user id {}",userId);
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<CaSubCaService> pageService = caSubCaServiceRepository.findAll(pageable);
        List<CaSubCaService> caSubCaServices = pageService.getContent();
        return caSubCaServices;
    }
}
