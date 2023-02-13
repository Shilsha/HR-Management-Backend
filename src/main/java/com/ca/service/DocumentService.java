package com.ca.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ca.entity.Customer;
import com.ca.entity.Document;
import com.ca.entity.User;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.print.Doc;
import java.io.IOException;
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

    public Document uploadDocument(Long customerId,Long customerUserId ,MultipartFile file) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (!customer.isPresent()) {
            throw new BadReqException("Customer Not Present");
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String fileNameWithoutExt = FilenameUtils.removeExtension(fileName);
        fileName = fileNameWithoutExt + "" + System.currentTimeMillis() + "" + extension;
        logger.info("File name {}",fileName);

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
                    .customerId(customerId)
                    .customerUserId(customerUserId)
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
    }

    public List<Document> getDocument(Long customerId, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Document> pageDocument = documentRepository.findAll(pageable);
        List<Document> documentList = pageDocument.getContent();

        if (documentList.isEmpty()){
            logger.info("Customer id not present in DB");
            throw new BadReqException("CustomerId not found");
        }else {
            logger.info("Document List send successfully");
            return documentList;
        }
    }

    public List<DocumentResponseDto> searchDocument(String docName) {

        List<Document> document = documentRepository.findByName(docName);
        List<DocumentResponseDto> documentResponseList = new ArrayList<>();
        logger.info("Search Document name start from {}",docName);

        for (Document document1: document){
            DocumentResponseDto documentResponse = DocumentResponseDto.builder()
                    .id(document1.getId())
                    .customerId(document1.getCustomerId())
                    .customerUserId(document1.getCustomerUserId())
                    .docName(document1.getDocName())
                    .docUrl(document1.getDocUrl())
                    .build();

            documentResponseList.add(documentResponse);
        }
        return documentResponseList;
    }
}