package com.example.demo.uploads;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.element.Element;

@RestController
@RequestMapping(path = "upload/")
public class UploadController {
	
	@Autowired
	private UploadService uploadService; 
	
	@GetMapping
	public List<Upload> list(){
		return uploadService.list();
	}
	
	@PostMapping(path="file/")
	private ResponseEntity<Object> importFile(@RequestParam("file") MultipartFile file) {
		
		HttpStatus status = HttpStatus.OK;
		
		try {
			
			/*
			 * Validating File Uploaded
			 */
			
			if(!file.getContentType().equals("text/csv") && !file.getContentType().equals("text/plain")) {
				throw new IllegalStateException("Invalid Content Type");
			} else if(file.isEmpty()) {
				throw new IllegalStateException("Content NOT found");
			}
			
			/*
			 * Store file
			 */
			uploadService.importFile(file);
			
			/*
			 * Parse file
			 */
			uploadService.parseFile();
			
		} catch (IllegalStateException | IOException | InterruptedException | ParseException e) {
			System.out.println(e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(status);
	}

}
