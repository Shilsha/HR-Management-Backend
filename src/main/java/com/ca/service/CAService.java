package com.ca.service;

import com.ca.controller.CAController;
import com.ca.controller.UserController;
import com.ca.entity.CA;
import com.ca.entity.CaSubCaService;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.model.response.CaServiceResponseDto;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.CARepository;
import com.ca.repository.CaSubCaServiceRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private UserRepository userRepository;

    public CA createCA(CA ca) {
        logger.info("New CA Created");
        return caRepository.save(ca);
    }

    public List<UserResponseDto> getAllCA(Integer pageNumber, Integer pageSize) {
        logger.info("Getting all CA");
        List<User> user = new ArrayList<>();
        int role = 1;

        if (pageNumber == -1 && pageSize == -1){
            user = userRepository.findByRole(role);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<User> pageCa = userRepository.findByrole(role, pageable);
            user = pageCa.getContent();
        }

        List<UserResponseDto> userResponse = new ArrayList<>();

        for (User user1: user)
        {
            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(user1.getId())
                    .firstName(user1.getFirstName())
                    .lastName(user1.getLastName())
                    .email(user1.getEmail())
                    .address(user1.getAddress())
                    .mobile(user1.getMobile())
                    .phone(user1.getPhone())
                    .role(user1.getRole())
                    .otp(user1.getOtp())
                    .otpVerify(user1.isOtpVerify())
                    .status(user1.isStatus())
                    .createdDate(user1.getCreatedDate())
                    .modifiedDate(user1.getModifiedDate())
                    .profileUrl(user1.getProfileUrl())
                    .profileName(user1.getProfileName())
                    .gender(user1.getGender())
                    .panCardNumber(user1.getPanCardNumber())
                    .build();

            userResponse.add(userResponseDto);
        }

        return userResponse;
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
