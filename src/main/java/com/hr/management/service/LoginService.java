package com.hr.management.service;

import com.hr.management.entity.EmployeeDetails;
import com.hr.management.entity.ForgotPasswordOTPDetails;
import com.hr.management.entity.HrDetails;
import com.hr.management.repo.EmpRepository;
import com.hr.management.repo.ForgotPasswordRepo;
import com.hr.management.repo.HrRepositoy;
import com.hr.management.request.ForgetPasswordRequestDto;
import com.hr.management.request.LoginRequestDto;
import com.hr.management.response.LoginResponseDto;
import com.hr.management.utils.ApiMessage;
import com.hr.management.utils.ApiResponse;
import com.hr.management.utils.BadReqException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class LoginService {

    @Autowired
    HrRepositoy hrRepositoy;
    @Autowired
    EmpRepository empRepository;

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    ForgotPasswordRepo forgotPasswordRepo;

    public ApiResponse hrlogin(LoginRequestDto loginRequestDto) throws Exception {
        //logger.info("The user is candidate");
        ApiResponse apiResponse=null;
        //apiResponse=new ApiResponse(HttpStatus.OK, true, hrLoginService.hrlogin(loginRequestDto), ApiMessage.Api_Message);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();

        if (loginRequestDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            loginResponseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;
            //throw new BadReqException(ApiMessage.ENTER_EMAIL);
        }
        if (loginRequestDto.getPassword().isEmpty() || loginRequestDto.getPassword().length() < 4) {
            // logger.info("please Enter valid password");
            loginResponseDto.setMessage(ApiMessage.ENTER_PASSWORD);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_PASSWORD);
            return apiResponse;
            // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
        }
        if (!loginRequestDto.getEmail().isEmpty() || !loginRequestDto.getPassword().isEmpty()) {
            HrDetails hr = hrRepositoy.findByEmail(loginRequestDto.getEmail());
            if (hr != null) {
                // HrDetails hrPassword = hrRepositoy.findByPassword(loginRequestDto.getPassword());
                HrDetails hrPassword = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                if (hrPassword == null) {
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_password);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_password);
                    return apiResponse;
                } else {
                    HrDetails res = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                    loginResponseDto = new LoginResponseDto(res.getId(), res.getFirstName());
                    loginResponseDto.setMessage("You have successful login");
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                    return apiResponse;
                }
            } else {
                loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                return apiResponse;
            }
        }
        /*HrDetails hr = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (hr == null) {

           // logger.info("Invalid credentials in login candidate");
            loginResponseDto.setMessage(ApiMessage.INVALID_credential);
            //throw new BadReqException(ApiMessage.INVALID_credential);
        }
        else{
        loginResponseDto = new LoginResponseDto(hr.getEmployeeId(),hr.getFirstName());
            loginResponseDto.setMessage("You have successful login");}*/
        return apiResponse;
    }

    public ApiResponse emplogin(LoginRequestDto loginRequestDto) throws Exception {
        //logger.info("The user is candidate");
        ApiResponse apiResponse= null;
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();

        if (loginRequestDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            loginResponseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;
            //throw new BadReqException(ApiMessage.ENTER_EMAIL);
        }
        if (loginRequestDto.getEmail().isEmpty() || loginRequestDto.getPassword().length() < 4) {
            // logger.info("please Enter valid password");
            loginResponseDto.setMessage(ApiMessage.ENTER_PASSWORD);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_PASSWORD);
            return apiResponse;
            // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
        }
        if (!loginRequestDto.getEmail().isEmpty() || !loginRequestDto.getPassword().isEmpty()) {
            EmployeeDetails emp = empRepository.findByEmail(loginRequestDto.getEmail());
            if (emp != null) {
                // EmployeeDetails empPassword = empRepository.findByPassword(loginRequestDto.getPassword());
                EmployeeDetails empPassword = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                if (empPassword == null) {
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_password);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_password);
                    return apiResponse;
                } else {
                    EmployeeDetails res = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                    loginResponseDto = new LoginResponseDto(res.getId(), res.getFirstName());
                    loginResponseDto.setMessage("You have successful login");
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                    return apiResponse;
                }
            } else {
                loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                return apiResponse;
            }
        }
        /*HrDetails hr = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (hr == null) {

           // logger.info("Invalid credentials in login candidate");
            loginResponseDto.setMessage(ApiMessage.INVALID_credential);
            //throw new BadReqException(ApiMessage.INVALID_credential);
        }
        else{
        loginResponseDto = new LoginResponseDto(hr.getEmployeeId(),hr.getFirstName());
            loginResponseDto.setMessage("You have successful login");}*/
        return apiResponse;
    }


    public ApiResponse forgetPasswordEmailVerify(LoginRequestDto loginRequestDto) throws  Exception {
        //logger.info("The user is candidate");
        ApiResponse apiResponse=null;

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();

        if (loginRequestDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            loginResponseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;
            //throw new BadReqException(ApiMessage.ENTER_EMAIL);
        }
        if (loginRequestDto.getEmpType().isEmpty() || (!loginRequestDto.getEmpType().equals("HR") & !loginRequestDto.getEmpType().equals("EMPLOYEE"))) {
            // logger.info("please Enter valid password");
            loginResponseDto.setMessage(ApiMessage.ENTER_EMP_TYPE);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_EMP_TYPE);
            return apiResponse;
            // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
        }
        if (!loginRequestDto.getEmail().isEmpty()) {
            if (loginRequestDto.getEmpType().equals("EMPLOYEE")) {
                EmployeeDetails emp = empRepository.findByEmail(loginRequestDto.getEmail());
                if (emp != null) {
                    loginResponseDto = new LoginResponseDto(emp.getId(), emp.getFirstName());
                    loginResponseDto.setMessage("Your email successful verify");
                    loginResponseDto.setEmail(emp.getEmail());
                    ForgetPasswordRequestDto requestDto = new ForgetPasswordRequestDto();
                    requestDto.setEmpId(emp.getId());
                    requestDto.setFirstName(emp.getFirstName());
                    requestDto.setEmail(emp.getEmail());
                    try {
                        sendEmailToAdmin(requestDto);
                    } catch (Exception e) {
                        System.out.println("send mail error" + e);
                    }
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                    return apiResponse;
               /* // EmployeeDetails empPassword = empRepository.findByPassword(loginRequestDto.getPassword());
                EmployeeDetails empPassword = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                if(empPassword==null){
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_password);
                    return loginResponseDto;
                }
                else{
                    EmployeeDetails res = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                    loginResponseDto = new LoginResponseDto(res.getEmployeeId(),res.getFirstName());
                    loginResponseDto.setMessage("You have successful login");
                }*/
                } else {
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                    return apiResponse;
                }
            }
            if (loginRequestDto.getEmpType().equals("HR")) {
                HrDetails hr = hrRepositoy.findByEmail(loginRequestDto.getEmail());
                if (hr != null) {
                    loginResponseDto = new LoginResponseDto(hr.getId(), hr.getFirstName());
                    loginResponseDto.setMessage("Your email successful verify");
                    loginResponseDto.setEmail(hr.getEmail());
                    ForgetPasswordRequestDto requestDto = new ForgetPasswordRequestDto();
                    requestDto.setEmpId(hr.getId());
                    requestDto.setFirstName(hr.getFirstName());
                    requestDto.setEmail(hr.getEmail());
                    try {
                        sendEmailToAdmin(requestDto);
                    } catch (Exception e) {
                        System.out.println("send mail error" + e);
                    }
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                    return apiResponse;
                } else {
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                    apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                    return apiResponse;
                }
            } else {
                loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                return apiResponse;
            }
        }
        /*HrDetails hr = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (hr == null) {

           // logger.info("Invalid credentials in login candidate");
            loginResponseDto.setMessage(ApiMessage.INVALID_credential);
            //throw new BadReqException(ApiMessage.INVALID_credential);
        }
        else{
        loginResponseDto = new LoginResponseDto(hr.getEmployeeId(),hr.getFirstName());
            loginResponseDto.setMessage("You have successful login");}*/
        return apiResponse;
    }

    public ApiResponse hrForgetCreatePassword(LoginRequestDto loginRequestDto) throws Exception{
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        ApiResponse apiResponse= null;
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();

        if (loginRequestDto.getEmail().isEmpty() || !emailValidation) {
            // logger.info("Please Enter valid email");
            loginResponseDto.setMessage(ApiMessage.ENTER_EMAIL);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_EMAIL);
            return apiResponse;
            //throw new BadReqException(ApiMessage.ENTER_EMAIL);
        }
        if (loginRequestDto.getPassword().isEmpty() || loginRequestDto.getPassword().length() < 4) {
            // logger.info("please Enter valid password");
            loginResponseDto.setMessage(ApiMessage.ENTER_PASSWORD);
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_PASSWORD);
            return apiResponse;
            // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
        }
        if (!loginRequestDto.getEmail().isEmpty() || !loginRequestDto.getPassword().isEmpty()) {
            HrDetails hr = hrRepositoy.findByEmail(loginRequestDto.getEmail());
            if (hr != null) {
                    hr.setPassword(loginRequestDto.getPassword());
                    hrRepositoy.save(hr);
                    loginResponseDto = new LoginResponseDto(hr.getId(), hr.getFirstName());
                    loginResponseDto.setMessage("You have successfully update password");
                apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                return apiResponse;
                }
            else {
            EmployeeDetails emp = empRepository.findByEmail(loginRequestDto.getEmail());
            if (emp != null) {
                emp.setPassword(loginRequestDto.getPassword());
                empRepository.save(emp);
                loginResponseDto = new LoginResponseDto(emp.getId(), emp.getFirstName());
                loginResponseDto.setMessage("You have successfully update password");
                apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.Api_Message);
                return apiResponse;
            }
            else {
                loginResponseDto.setMessage(ApiMessage.INVALID_credential_email);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.INVALID_credential_email);
                return apiResponse;
            }
            }
        }
        /*HrDetails hr = hrRepositoy.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (hr == null) {

           // logger.info("Invalid credentials in login candidate");
            loginResponseDto.setMessage(ApiMessage.INVALID_credential);
            //throw new BadReqException(ApiMessage.INVALID_credential);
        }
        else{
        loginResponseDto = new LoginResponseDto(hr.getEmployeeId(),hr.getFirstName());
            loginResponseDto.setMessage("You have successful login");}*/
        return apiResponse;

    }

    public LoginResponseDto sendEmailToAdmin(ForgetPasswordRequestDto forgetPasswordRequestDto) throws MessagingException, BadReqException {

        if (!forgetPasswordRequestDto.getEmail().isEmpty()) {
            ForgotPasswordOTPDetails forgotPasswordOTPDetails = new ForgotPasswordOTPDetails();
            forgotPasswordOTPDetails.setOtp(generateOtp());
            forgotPasswordOTPDetails.setEmployeeId(forgetPasswordRequestDto.getEmpId());
            System.out.println(generateOtp());

            if (!forgetPasswordRequestDto.getEmail().isEmpty()) {

                String toAddress = forgetPasswordRequestDto.getEmail();
                String subject = "Reset password";
                String content = "Dear " + forgetPasswordRequestDto.getFirstName() + ",<br>"
                        + "Please Enter this  reset code to reset Your password  sucessfully:<br>"
                        + "<h3>" + forgotPasswordOTPDetails.getOtp() + "</h3><br>"
                        + "Thank you,<br>"
                        + "Have a nice day,<br>"
                        + "The Ebench Team";
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(toAddress);
                helper.setSubject(subject);
                helper.setText(content, true);
                message.setContent(content, "text/html");
                javaMailSender.send(message);
                forgotPasswordOTPDetails.setOtpVerified(false);
                forgotPasswordRepo.save(forgotPasswordOTPDetails);
            } else {
                throw new BadReqException("Candidate Email not present");
            }
            return null;
        } else {
            throw new BadReqException("No email present");
        }
    }

    public static String generateOtp() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public ApiResponse forgetPasswordOtpVerify(ForgetPasswordRequestDto forgetPasswordRequestDto) throws Exception{
        //logger.info("The user is candidate");
        ApiResponse apiResponse = null;
        LoginResponseDto loginResponseDto = new LoginResponseDto();
            //throw new BadReqException(ApiMessage.ENTER_EMAIL);
            if(forgetPasswordRequestDto.getOtp().isEmpty() || forgetPasswordRequestDto.getOtp().length() < 6) {
                // logger.info("please Enter valid password");
                loginResponseDto.setMessage(ApiMessage.ENTER_OTP);
                apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, ApiMessage.ENTER_OTP);
                return apiResponse;
                // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
            }
        if(forgetPasswordRequestDto.getEmpId()==null) {
            // logger.info("please Enter valid password");
            loginResponseDto.setMessage("Please enter emp id");
            apiResponse=new ApiResponse(HttpStatus.BAD_REQUEST, false, loginResponseDto, "Please enter emp id");
            return apiResponse;
            // throw new BadReqException(ApiMessage.ENTER_PASSWORD);
        }

        if (!forgetPasswordRequestDto.getOtp().isEmpty()) {
                ForgotPasswordOTPDetails response= forgotPasswordRepo.findByEmpIdAAndOtp(forgetPasswordRequestDto.getEmpId(),forgetPasswordRequestDto.getOtp());
                if (response != null) {
                    loginResponseDto = new LoginResponseDto();
                    loginResponseDto.setEmployeeId(response.getEmployeeId());
                    loginResponseDto.setMessage("Otp successfully verify");
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, "Otp successfully verify");
                    return apiResponse;
               /* // EmployeeDetails empPassword = empRepository.findByPassword(loginRequestDto.getPassword());
                EmployeeDetails empPassword = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                if(empPassword==null){
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_password);
                    return loginResponseDto;
                }
                else{
                    EmployeeDetails res = empRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
                    loginResponseDto = new LoginResponseDto(res.getEmployeeId(),res.getFirstName());
                    loginResponseDto.setMessage("You have successful login");
                }*/
                } else {
                    loginResponseDto.setMessage(ApiMessage.INVALID_credential_otp);
                    apiResponse=new ApiResponse(HttpStatus.OK, true, loginResponseDto, ApiMessage.INVALID_credential_otp);
                    return apiResponse;
                }
            }
        return apiResponse;
    }
}
