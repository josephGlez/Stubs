package org.tech.demo.amazon.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Entity
@Table(schema="public", name="File_Upload_Info")
public class FileUploadInfo {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long userId;
	private String url;
	private String fileNameInBucket;
	private String originalFileName;
	
	public FileUploadInfo(final Long userId, final String url, final String fileNameInBucket, final String originalFileName) {
		this.userId = userId;
		this.url = url;
		this.fileNameInBucket = fileNameInBucket;
		this.originalFileName = originalFileName;
	}

}
