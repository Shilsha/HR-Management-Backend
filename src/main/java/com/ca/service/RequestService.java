package com.ca.service;

import com.ca.entity.Request;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.model.response.RequestResponse;
import com.ca.repository.RequestRepository;
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
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public Request addRequest(Request request) {
        logger.info("Add Request in DB");
        request.setRequestStatus(true);
        request.setIsResolved(true);
        return requestRepository.save(request);
    }

    public List<RequestResponse> getRequestByCAId(Long caId, Integer pageNumber, Integer pageSize) {

        logger.info("Get Request by CA id : {}",caId);
        List<Request> requests = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            requests = requestRepository.findByCAId(caId);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<Request> pageRequest = requestRepository.findByCAid(caId,pageable);
            requests = pageRequest.getContent();
        }

        List<RequestResponse> requestResponses = new ArrayList<>();

        for (Request request : requests){

            User ca = userRepository.findById(caId).get();

            User customer = userRepository.findById(request.getCustomerId()).get();

            RequestResponse requestResponse = RequestResponse.builder()
                    .requestId(request.getId())
                    .text(request.getText())
                    .caId(request.getCaId())
                    .caName(ca.getFirstName()+" "+ca.getLastName())
                    .customerId(request.getCustomerId())
                    .customerName(customer.getFirstName()+" "+customer.getLastName())
                    .createdDate(request.getCreatedDate())
                    .modifiedDate(request.getModifiedDate())
                    .isResolved(request.getIsResolved())
                    .requestStatus(request.getRequestStatus())
                    .build();

            requestResponses.add(requestResponse);
        }

        logger.info("Request send successfully");
        return requestResponses;
    }

    public List<RequestResponse> getRequestByCustomerId(Long customerId, Integer pageNumber, Integer pageSize) {

        logger.info("Get Request by customer id : {}", customerId);
        List<Request> requests = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            requests = requestRepository.findByCustomerId(customerId);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<Request> pageRequest = requestRepository.findByCustomerid(customerId,pageable);
            requests = pageRequest.getContent();
        }

        List<RequestResponse> requestResponses = new ArrayList<>();

        for (Request request : requests){
            User ca = userRepository.findById(request.getCaId()).get();
            User customer = userRepository.findById(request.getCustomerId()).get();

            RequestResponse requestResponse = RequestResponse.builder()
                    .requestId(request.getId())
                    .text(request.getText())
                    .caId(request.getCaId())
                    .caName(ca.getFirstName()+" "+ca.getLastName())
                    .customerId(request.getCustomerId())
                    .customerName(customer.getFirstName()+" "+customer.getLastName())
                    .createdDate(request.getCreatedDate())
                    .modifiedDate(request.getModifiedDate())
                    .isResolved(request.getIsResolved())
                    .requestStatus(request.getRequestStatus())
                    .build();

            requestResponses.add(requestResponse);
        }
        logger.info("Request send successfully");
        return requestResponses;
    }

    public Request deleteRequest(Long requestId) {
        logger.info("Delete request id : {}",requestId);

        Optional<Request> request = requestRepository.findById(requestId);
        if (!request.isPresent()){
            throw new BadReqException("Request id not present in DB");
        }
        Request request1 = request.get();
        request1.setRequestStatus(false);
        return requestRepository.save(request1);

    }

    public Request resolved(Long requestId) {

        logger.info("Request resolving");
        Optional<Request> request = requestRepository.findById(requestId);

        if (!request.isPresent()){
            throw new BadReqException("Request not present in DB");
        }

        Request request1 = request.get();

        request1.setIsResolved(false);
        return requestRepository.save(request1);
    }

}
