package org.tech.demo.amazon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tech.demo.amazon.persistence.model.FileUploadInfo;
import org.tech.demo.amazon.service.AmazonClientService;

@RestController
@CrossOrigin
public class Controller {

    private AmazonClientService amazonClientService;
    
	  @Autowired
	    Controller(AmazonClientService amazonClient) {
	        this.amazonClientService = amazonClient;
	    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam(required = true) Long userId, @RequestPart(value = "file") MultipartFile file) {
        return this.amazonClientService.uploadFile(userId,file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestParam(required = true) String fileUrl) {
        return this.amazonClientService.deleteFileFromS3Bucket(fileUrl);
    }
    
    @GetMapping("/retrieveAll")
    public List<FileUploadInfo> retrieveAll(@RequestParam(required = true) Long userId) {
        return this.amazonClientService.getAllFiles(userId);
    }

}
