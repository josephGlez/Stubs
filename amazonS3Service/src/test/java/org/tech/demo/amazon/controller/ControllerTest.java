package org.tech.demo.amazon.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.tech.demo.amazon.persistence.model.FileUploadInfo;
import org.tech.demo.amazon.service.AmazonClientService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AmazonClientService amazonClientService;
	
	@Test
	public void testRetrieveAll() throws Exception {
		final Long id = 1L;
		List<FileUploadInfo> expectedFileList = new ArrayList<>();
		FileUploadInfo file = new FileUploadInfo(id, "test", "testFileBckt", "tefile");
		expectedFileList.add(file);
		
		Mockito.when(amazonClientService.getAllFiles(id)).thenReturn(expectedFileList);
		this.mockMvc.
			perform(get("/retrieveAll?userId="+id)).
			andExpect(status().isOk());
	}

	@Test
	public void testDelete() throws Exception {
		final String fileUrl = "url";
		final String expectedResponse = "Success";
		Mockito.when(amazonClientService.deleteFileFromS3Bucket(fileUrl )).thenReturn(expectedResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteFile?fileUrl="+fileUrl);
		
		MvcResult result = this.mockMvc.perform(requestBuilder)
				.andExpect(status().isOk()).andReturn();
		final String actualResponse = result.getResponse().getContentAsString();
		Assert.assertEquals(expectedResponse, actualResponse);
	}
	
}
