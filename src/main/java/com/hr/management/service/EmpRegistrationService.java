package com.hr.management.service;

import com.hr.management.entity.EmployeeDetails;
import com.hr.management.repo.EmpRepository;
import com.hr.management.request.EmployeeReqDto;
import com.hr.management.response.ResponseDto;
import com.hr.management.utils.ApiMessage;
import com.hr.management.utils.ApiResponse;
import com.hr.management.utils.BadReqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
@Service
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmpRegistrationService {

    @Autowired
    EmpRepository empRepository;
    public ApiResponse registor(EmployeeReqDto employeeReqDto) throws Exception{

        ApiResponse apiResponse= null;
        ResponseDto responseDto = new ResponseDto();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(employeeReqDto.getEmail())
                .matches();

        if (emailAlreadyExist(employeeReqDto.getEmail())) {
            System.out.println("User Already Exist");
            responseDto.setMessage(ApiMessage.EMAIL_ALREADY_USED);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.EMAIL_ALREADY_USED);
            return apiResponse;
        }

        EmployeeDetails employeeDetails = new EmployeeDetails();
        if (employeeReqDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            responseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;}
        else {
            employeeDetails.setEmail(employeeReqDto.getEmail());
        }
        try {
            employeeDetails.setEmployeeId(employeeReqDto.getEmployeeId());
            employeeDetails.setFirstName(employeeReqDto.getFirstName());
            employeeDetails.setLastName(employeeReqDto.getLastName());
           // employeeDetails.setEmail(employeeReqDto.getEmail());
            employeeDetails.setDesignation(employeeReqDto.getDesignation());
            employeeDetails.setJoiningDate(employeeReqDto.getJoiningDate());
            employeeDetails.setRecruiterName(employeeReqDto.getRecruiterName());
            employeeDetails.setEmpType(employeeReqDto.getEmpType());
            employeeDetails.setCurrentAddress(employeeReqDto.getCurrentAddress());
            employeeDetails.setTotalExperience(employeeReqDto.getTotalExperience());
            employeeDetails.setDob(employeeReqDto.getDob());


            if (employeeReqDto.getPhoneNumber().isEmpty() || employeeReqDto.getPhoneNumber().length() != 10) {
                responseDto.setMessage(ApiMessage.Enter_Valid_Phone_Number);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.Enter_Valid_Phone_Number);
                return apiResponse;
                //throw new BadReqException(ApiMessage.Enter_Valid_Phone_Number);
            } else {
                employeeDetails.setPhoneNumber(employeeReqDto.getPhoneNumber());
            }

            empRepository.save(employeeDetails);
            responseDto.setMessage(ApiMessage.SUCESSFULLY_CREATED);
            apiResponse=new ApiResponse(HttpStatus.OK, true, responseDto, ApiMessage.SUCESSFULLY_CREATED);
            //sendVerificationEmail(employeeDetails);
            return apiResponse;

        } catch (BadReqException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;

        } catch (IOException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;
        }
    }
    public ApiResponse update(EmployeeReqDto employeeReqDto) throws Exception{

        ApiResponse apiResponse= null;
        ResponseDto responseDto = new ResponseDto();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(employeeReqDto.getEmail())
                .matches();

        /*if (emailAlreadyExist(employeeReqDto.getEmail())) {
            System.out.println("User Already Exist");
            responseDto.setMessage(ApiMessage.EMAIL_ALREADY_USED);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.EMAIL_ALREADY_USED);
            return apiResponse;
        }*/

       /* if (employeeReqDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            responseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;}
        else {
            employeeDetails.setEmail(employeeReqDto.getEmail());
        }*/
        try {
            Optional<EmployeeDetails> employee = empRepository.findById(employeeReqDto.getId());
            if (employee.isPresent()) {
                EmployeeDetails employeeDetails=employee.get();
                employeeDetails.setEmployeeId(employeeDetails.getEmployeeId());
                employeeDetails.setFirstName(employeeReqDto.getFirstName());
                employeeDetails.setLastName(employeeReqDto.getLastName());
                // employeeDetails.setEmail(employeeReqDto.getEmail());
                employeeDetails.setDesignation(employeeReqDto.getDesignation());
                employeeDetails.setJoiningDate(employeeReqDto.getJoiningDate());
                employeeDetails.setRecruiterName(employeeReqDto.getRecruiterName());
                employeeDetails.setEmpType(employeeReqDto.getEmpType());
                employeeDetails.setCurrentAddress(employeeReqDto.getCurrentAddress());
                employeeDetails.setTotalExperience(employeeReqDto.getTotalExperience());
                employeeDetails.setDob(employeeReqDto.getDob());


                if (employeeReqDto.getPhoneNumber().isEmpty() || employeeReqDto.getPhoneNumber().length() != 10) {
                    responseDto.setMessage(ApiMessage.Enter_Valid_Phone_Number);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.Enter_Valid_Phone_Number);
                    return apiResponse;
                    //throw new BadReqException(ApiMessage.Enter_Valid_Phone_Number);
                } else {
                    employeeDetails.setPhoneNumber(employeeReqDto.getPhoneNumber());
                }

                if (employeeReqDto.getEmail().isEmpty() || !emailValidation) {
                    // logger.info("Please Enter valid email");
                    responseDto.setMessage(ApiMessage.ENTER_EMAIL);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.ENTER_EMAIL);
                    return apiResponse;}
                else {
                    employeeDetails.setEmail(employeeReqDto.getEmail());
                }
                empRepository.save(employeeDetails);
                responseDto.setMessage(ApiMessage.SUCESSFULLY_UPDFATED);
                apiResponse=new ApiResponse(HttpStatus.OK, true, responseDto, ApiMessage.SUCESSFULLY_UPDFATED);
                //sendVerificationEmail(employeeDetails);
                return apiResponse;
            } else {
                System.out.println("False");
                responseDto.setMessage(ApiMessage.DATA_MESSAGE);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.DATA_MESSAGE);
                //sendVerificationEmail(employeeDetails);
                return apiResponse;
            }


        } catch (BadReqException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;

        } catch (IOException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;
        }
    }
    public ApiResponse listofEmp() throws Exception{

        ApiResponse apiResponse= null;
        ResponseDto responseDto = new ResponseDto();
        try {
            List<EmployeeDetails> employee = empRepository.findAll();
            if (employee.size()>0) {
                responseDto.setMessage(ApiMessage.LIST_EMP);
                responseDto.setEmployeeDetailsList(employee);
                apiResponse=new ApiResponse(HttpStatus.OK, true, responseDto, ApiMessage.LIST_EMP);
                //sendVerificationEmail(employeeDetails);
                return apiResponse;
            } else {
                System.out.println("False");
                responseDto.setMessage(ApiMessage.LIST_EMP_NOT_FOUND);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.LIST_EMP_NOT_FOUND);
                //sendVerificationEmail(employeeDetails);
                return apiResponse;
            }


        } catch (BadReqException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;

        } catch (IOException e) {
            responseDto.setMessage(ApiMessage.INTERNAL_ERROR);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, responseDto, ApiMessage.INTERNAL_ERROR);
            return apiResponse;
        }
    }
    public Boolean emailAlreadyExist(String email) {
        System.out.println("In Email Exist Checking Method");
         Optional<EmployeeDetails> employeeDetails = empRepository.findUserByEmail(email);
        if (employeeDetails.isPresent()) {
            System.out.println("True");
            return true;
        } else {
            System.out.println("False");
            return false;
        }
    }

}
