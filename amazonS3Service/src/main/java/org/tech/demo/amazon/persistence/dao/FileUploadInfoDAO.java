package org.tech.demo.amazon.persistence.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tech.demo.amazon.persistence.model.FileUploadInfo;

public interface FileUploadInfoDAO extends JpaRepository<FileUploadInfo, Long> {
	
	List<FileUploadInfo> findAllByUserId(final Long UserId);
	Long deleteByUrl(final String url);
}
