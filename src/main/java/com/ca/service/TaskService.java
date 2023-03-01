package com.ca.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ca.entity.Task;
import com.ca.exception.BadReqException;
import com.ca.repository.TaskRepository;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Value("${application.max-file-size}")
    private Double maxFileSize;
    @Value("${application.min-file-size}")
    private Double minFileSize;
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${application.bucket.name}")
    private String bucketName;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public Task addTask(Task task) {
        logger.info("Add new task");

        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId) {
        logger.info("Get task by id :{}",taskId);

        Optional<Task> task = taskRepository.findById(taskId);
        if (!task.isPresent()){
            throw new BadReqException("Task not present in DB id :"+taskId);
        }
        return task.get();
    }

    public Task deleteTask(Long taskId) {

        logger.info("Delete task by id {}",taskId);
        Optional<Task> task = taskRepository.findById(taskId);
        if (!task.isPresent()){
            throw new BadReqException("Task not present in DB");
        }

        Task task1 = task.get();
        task1.setTaskStatus(false);
        return taskRepository.save(task1);
    }

    public Task updateTask(Task task) {
        logger.info("Update task by id {}",task.getId());

        Optional<Task> task1 = taskRepository.findById(task.getId());
        if(!task1.isPresent()){
            throw new BadReqException("Task not present in DB id :"+task.getId());
        }
        return taskRepository.save(task);
    }

    public Task uploadTaskDocument(Long taskId, MultipartFile file) {

        logger.info("Upload document in task");
        Optional<Task> task = taskRepository.findById(taskId);
        if (!task.isPresent()){
            throw new BadReqException("Task id not present in DB");
        }
        Task task1 = task.get();

        String documentName = file.getOriginalFilename();
        String extension = documentName.substring(documentName.lastIndexOf("."));
        String documentNameWithoutExt = FilenameUtils.removeExtension(documentName);
        documentName = documentNameWithoutExt + "" + System.currentTimeMillis() + "" + extension;
        logger.info("Profile name {}",documentName);

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

            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                amazonS3.putObject(new PutObjectRequest(bucketName, documentName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicReadWrite));

                String documentUrl = "https://ebench-images.s3.ap-south-1.amazonaws.com/" + documentName;
                logger.info("Document URL : {}",documentUrl);

                task1.setDocName(documentName);
                task1.setDocUrl(documentUrl);

                Task task2 = taskRepository.save(task1);
                logger.info("Document Uploaded Successfully!!");
                return task2;

        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new BadReqException("Only (.jpg, .pdf, .word, .docx, .svg, .png, .xlsx) type of file are allowed!!");
        }
    }
}
