package com.ca.service;

import com.ca.entity.CaSubCaService;
import com.ca.entity.Notification;
import com.ca.entity.SubCA;
import com.ca.entity.User;
import com.ca.model.response.SubCAResponseDto;
import com.ca.model.response.SubCAServiceResponseDto;
import com.ca.repository.CaSubCaServiceRepository;
import com.ca.repository.NotificationRepository;
import com.ca.repository.SubCARepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubCAService {

    @Autowired
    private SubCARepository subCARepository;
    @Autowired
    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(SubCAService.class);
    @Autowired
    private CaSubCaServiceRepository caSubCaServiceRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public SubCA create(SubCA subCA){
        logger.info("Create a SubCA");
        return subCARepository.save(subCA);
    }

    public List<SubCA> getAllSubCA(Integer pageNumber, Integer pageSize){
        logger.info("Getting all SubCA");
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<SubCA> pageSubCa = subCARepository.findAll(pageable);
        List<SubCA> subCA = pageSubCa.getContent();
        return subCA;
    }

    public SubCA getSingleSubCA(Long id){
        logger.info("Getting a SubCA id:"+id);
        return subCARepository.findById(id).get();
    }

    public void updateSubCA(SubCA subCA, Long id){
        Optional<SubCA> subCA1 = subCARepository.findById(id);
        if (subCA1.isPresent()){
            logger.info("Update the SubCA id:" +id);
            SubCA subCA2 = subCA1.get();
            subCA2.setAddedBy(subCA.getAddedBy());
            subCA2.setCaId(subCA.getCaId());
        }
    }

    public void deleteSubCA(Long id){
        logger.info("Deleting the SubCA id:"+id);
        subCARepository.deleteById(id);
    }

    public List<SubCAResponseDto> getSubCAByCAId(Long caId) {
        logger.info("Getting all sub CA have CAId :{}",caId);
        List<SubCA> subCA = subCARepository.findByCAId(caId);

        List<SubCAResponseDto> subCAResponse = new ArrayList<>();

        for (SubCA subCA1: subCA) {
            User user = userRepository.findById(subCA1.getUserId()).get();
            SubCAResponseDto s = SubCAResponseDto.builder()
                    .id(subCA1.getId())
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .mobile(user.getMobile())
                    .phone(user.getPhone())
                    .build();

            subCAResponse.add(s);
        }

        logger.info("Get list of SubCA have CA Id {}",caId);
        return subCAResponse;
    }

    public List<SubCAServiceResponseDto> getServices(Long subCaId) {

        List<SubCAServiceResponseDto> subCAService = new ArrayList<>();

        SubCA subCA = subCARepository.findById(subCaId).get();
        List<CaSubCaService> service = caSubCaServiceRepository.findByUserId(subCA.getUserId());

        for(CaSubCaService service1: service){

            SubCAServiceResponseDto subCAServiceResponse = SubCAServiceResponseDto.builder()
                    .serviceId(service1.getId())
                    .subCaId(subCaId)
                    .serviceName(service1.getServiceName())
                    .serviceDesc(service1.getServiceDesc())
                    .build();

            subCAService.add(subCAServiceResponse);
        }

        logger.info("Get service of SubCA id: {}",subCaId);
        return subCAService;
    }
}
