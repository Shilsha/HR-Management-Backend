package com.ca.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ca.entity.Customer;
import com.ca.entity.Document;
import com.ca.exception.BadReqException;
import com.ca.model.response.DocumentResponseDto;
import com.ca.repository.CustomerRepository;
import com.ca.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.print.Doc;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

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

    public Document uploadDocument(Long userId ,MultipartFile file) {

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
                        .build();

                return documentRepository.save(document);

            } catch (IOException ioe) {
                logger.error("IOException: " + ioe.getMessage());
            } catch (AmazonServiceException serviceException) {
                logger.info("AmazonServiceException: " + serviceException.getMessage());
                throw serviceException;
            } catch (AmazonClientException clientException) {
                logger.info("AmazonClientException Message: " + clientException.getMessage());
                throw clientException;
            }

            logger.info("Document uploaded successfully");
            return document;
        }else {
            throw new BadReqException("Only (.jpg, .pdf, .word, .docx, .svg, .png, .xlsx) type of file are allowed!!");
        }
    }

    public List<Document> getDocument(Long userId, Integer pageNumber, Integer pageSize) {

        List<Document> documentList = new ArrayList<>();

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
            logger.info("Customer id not present in DB");
            throw new BadReqException("Customer not found");
        }else {
            logger.info("Document List send successfully");
            return documentList;
        }
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
                    .id(document1.getId())
                    .userId(document1.getUserId())
                    .docName(document1.getDocName())
                    .docUrl(document1.getDocUrl())
                    .build();

            documentResponseList.add(documentResponse);
        }
        return documentResponseList;
    }
}