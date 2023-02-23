package com.ca.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ca.entity.Customer;
import com.ca.entity.Document;
import com.ca.entity.Service;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.model.response.DocumentResponseDto;
import com.ca.repository.CaServiceRepository;
import com.ca.repository.CustomerRepository;
import com.ca.repository.DocumentRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AmazonS3 amazonS3;
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${application.max-file-size}")
    private Double maxFileSize;
    @Value("${application.min-file-size}")
    private Double minFileSize;
    @Autowired
    private CaServiceRepository caServiceRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    public Document uploadDocument(Long userId ,MultipartFile file, Long serviceId) throws MessagingException {

        Optional<Customer> customer = customerRepository.findByUserId(userId);

        if (!customer.isPresent()) {
            throw new BadReqException("Customer Not Present");
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        logger.info("Extension of file : {}",extension);
        String fileNameWithoutExt = FilenameUtils.removeExtension(fileName);
        fileName = fileNameWithoutExt + "" + System.currentTimeMillis() + "" + extension;
        logger.info("File name {}",fileName);

        if (extension.equals(".png") || extension.equals(".jpg")
                || extension.equals(".docx") || extension.equals(".svg")
                || extension.equals(".pdf") || extension.equals(".word") || extension.equals(".xlsx")) {

            Double fileSizeInBytes = (double) file.getSize();
            logger.info("File size in Byte {}",fileSizeInBytes);
            Double fileSizeInKB = fileSizeInBytes / 1024;
            logger.info("File size in KB {}",fileSizeInKB);

            if(fileSizeInKB > maxFileSize || fileSizeInKB < minFileSize){
                throw new BadReqException("File size min 2KB and max 5MB");
            }

            Document document = new Document();
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicReadWrite));

                String documentUrl = "https://ebench-images.s3.ap-south-1.amazonaws.com/" + fileName;
                System.out.println(documentUrl);

                document = Document.builder()
                        .docName(fileName)
                        .userId(userId)
                        .docUrl(documentUrl)
                        .serviceId(serviceId)
                        .build();

                documentRepository.save(document);

            } catch (IOException ioe) {
                logger.error("IOException: " + ioe.getMessage());
            } catch (AmazonServiceException serviceException) {
                logger.info("AmazonServiceException: " + serviceException.getMessage());
                throw serviceException;
            } catch (AmazonClientException clientException) {
                logger.info("AmazonClientException Message: " + clientException.getMessage());
                throw clientException;
            }

            User user = userRepository.findByid(userId);

            Customer customer1 = customer.get();
            User caDetails = userRepository.findByid(customer1.getCaId());
            logger.info("Document uploaded successfully");
            emailService.sendDocumentEmail(document, user.getFirstName(), caDetails.getEmail());
            logger.info("Document Mail send successfully on CA email : {}",caDetails.getEmail());
            return document;
        }else {
            throw new BadReqException("Only (.jpg, .pdf, .word, .docx, .svg, .png, .xlsx) type of file are allowed!!");
        }
    }

    public List<DocumentResponseDto> getDocument(Long userId, Integer pageNumber, Integer pageSize) {

        List<Document> documentList = new ArrayList<>();
        List<DocumentResponseDto> documentResponse = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            logger.info("Pagination not present");
            documentList = documentRepository.findByUserid(userId);
        }else {
            logger.info("Pagination present pageNumber :{}",pageNumber);
            logger.info("PageSize :{}",pageSize);
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Document> pageDocument = documentRepository.findByUserId(userId, pageable);
            documentList = pageDocument.getContent();
        }

        if (documentList.isEmpty()){
            logger.info("No document uploaded by customer");
            throw new BadReqException("No Document uploaded by the customer");
        }

        for(Document document: documentList){
            String serviceName=null;
            if (!((document.getServiceId() == null) || (document.getServiceId() == -1))){
                logger.info("Service id is {}",document.getServiceId());

                Optional<Service> service = caServiceRepository.findById(document.getServiceId());
                Service service1 = service.get();
                serviceName = service1.getServiceName();
            }

            DocumentResponseDto documentResponseDto = DocumentResponseDto.builder()
                    .docId(document.getId())
                    .docUrl(document.getDocUrl())
                    .docName(document.getDocName())
                    .serviceId(document.getServiceId())
                    .serviceName(serviceName)
                    .userId(document.getUserId())
                    .createdDate(document.getCreatedDate())
                    .modifiedDate(document.getModifiedDate())
                    .build();

            documentResponse.add(documentResponseDto);
        }

        logger.info("Document List send successfully");
        return documentResponse;

    }

    public List<DocumentResponseDto> searchDocument(String docName, Integer pageNumber, Integer pageSize) {

        List<Document> document = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            document = documentRepository.findByname(docName);
        }else {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Document> pageDocument = documentRepository.findByName(docName,pageable);
            document = pageDocument.getContent();
        }

        List<DocumentResponseDto> documentResponseList = new ArrayList<>();
        logger.info("Search Document name start from {}",docName);

        for (Document document1: document){
            DocumentResponseDto documentResponse = DocumentResponseDto.builder()
                    .docId(document1.getId())
                    .userId(document1.getUserId())
                    .docName(document1.getDocName())
                    .docUrl(document1.getDocUrl())
                    .serviceId(document1.getServiceId())
                    .createdDate(document1.getCreatedDate())
                    .modifiedDate(document1.getModifiedDate())
                    .build();

            documentResponseList.add(documentResponse);
        }
        return documentResponseList;
    }
}