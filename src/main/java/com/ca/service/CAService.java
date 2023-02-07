package com.ca.service;

import com.ca.controller.CAController;
import com.ca.controller.UserController;
import com.ca.entity.CA;
import com.ca.entity.CaSubCaService;
import com.ca.exception.BadReqException;
import com.ca.model.response.CaServiceResponseDto;
import com.ca.repository.CARepository;
import com.ca.repository.CaSubCaServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CAService {

    @Autowired
    private CARepository caRepository;
    private Logger logger = LoggerFactory.getLogger(CAController.class);
    @Autowired
    private CaSubCaServiceRepository caSubCaServiceRepository;

    public CA createCA(CA ca) {
        logger.info("New CA Created");
        return caRepository.save(ca);
    }

    public List<CA> getAllCA() {
        logger.info("Getting all CA");
        return caRepository.findAll();
    }

    public CA getSingleCA(Long caId){
        logger.info("Getting the CA, id :"+caId);
        return caRepository.findById(caId).get();
    }

    public void updateCA(CA ca, Long caId){
        Optional<CA> ca1 = caRepository.findById(caId);
        if (ca1.isPresent()){
            logger.info("Updating the CA id:"+ca.getId());
            CA ca2 = ca1.get();
            ca2.setUserId(ca.getUserId());
            ca2.setAdminId(ca.getAdminId());
        }
    }

    public void deleteCA(Long caId){
        logger.info("Deleting CA id:"+caId);
        caRepository.deleteById(caId);
    }

//    <-----------------------------------------Get CA Services -------------------------------------->
    public List<CaServiceResponseDto> getService(Long caId) {
        Optional<CA> ca = caRepository.findById(caId);
        List<CaServiceResponseDto> caServices = new ArrayList<>();

        if (ca.isPresent()){
            CA ca1 = ca.get();
            Long userId = ca1.getUserId();

            List<CaSubCaService> services = caSubCaServiceRepository.findByUserId(userId);
            for (CaSubCaService service: services) {
                CaServiceResponseDto caServiceResponse = CaServiceResponseDto.builder()
                        .serviceId(service.getId())
                        .serviceName(service.getServiceName())
                        .serviceDesc(service.getServiceDesc())
                        .caId(caId)
                        .build();

                caServices.add(caServiceResponse);
            }

            return caServices;

        }else {
            throw new BadReqException("CA id does not exist");
        }
    }
}
