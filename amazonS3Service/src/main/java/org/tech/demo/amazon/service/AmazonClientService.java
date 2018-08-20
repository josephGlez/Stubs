package org.tech.demo.amazon.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tech.demo.amazon.persistence.dao.FileUploadInfoDAO;
import org.tech.demo.amazon.persistence.model.FileUploadInfo;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class AmazonClientService {

	private AmazonS3 s3Client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@Autowired
	FileUploadInfoDAO fileUploadInfoDao;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3Client = new AmazonS3Client(credentials);
	}

	@Transactional(readOnly = false)
	public String uploadFile(final Long userId, MultipartFile multipartFile) {
		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileNameInBucket = generateFileName(multipartFile);
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileNameInBucket;
			uploadFileTos3bucket(fileNameInBucket, file);
			FileUploadInfo fileInfo = new FileUploadInfo(userId, fileUrl, fileNameInBucket,
					multipartFile.getOriginalFilename());
			fileUploadInfoDao.save(fileInfo);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;
	}

	@Transactional(readOnly = false)
	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		fileUploadInfoDao.deleteByUrl(fileUrl);
		return "Successfully deleted";
	}

	public List<FileUploadInfo> getAllFiles(final Long userId) {
		return fileUploadInfoDao.findAllByUserId(userId);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3Client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

}
