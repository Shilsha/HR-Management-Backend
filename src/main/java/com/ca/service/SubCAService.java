package com.ca.service;

import com.ca.entity.CaSubCaService;
import com.ca.entity.Notification;
import com.ca.entity.SubCA;
import com.ca.entity.User;
import com.ca.model.response.SubCAResponseDto;
import com.ca.model.response.SubCAServiceResponseDto;
import com.ca.model.response.UserResponseDto;
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

    public List<UserResponseDto> getAllSubCA(Integer pageNumber, Integer pageSize){
        logger.info("Getting all SubCA");
        int role = 3;
        List<User> user = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            user = userRepository.findByRole(role);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<User> userPage = userRepository.findByrole(role, pageable);
            user = userPage.getContent();
        }

        List<UserResponseDto> userResponse = new ArrayList<>();

        for (User user1: user) {
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
