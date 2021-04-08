package com.example.demo.uploads;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constants.FileStatus;
import com.example.demo.element.Element;
import com.example.demo.element.ElementService;

@Service
@EnableAsync
public class UploadService {

	private String fileName;
	
	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private ElementService elementService;

	@Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
		
        return new ThreadPoolTaskExecutor();
        
    }
	
	@Async("threadPoolTaskExecutor")
	public void parseFile() throws InterruptedException, ParseException {
		
		Upload upload = uploadRepository.findFirstByStatusOrderByTimestamp(FileStatus.INPROGRESS);
		
		if(null != upload) {
			
			Scanner sc = null;
			Integer lineNo 	= 0;
	    	List <String> elements = new ArrayList<>();
	    	try {
	
		    	sc = new Scanner( new File(upload.getName()));
			    
		    	while(sc.hasNextLine()){
		      
		    		String row = sc.nextLine();
		        
		    		/*
		    		 *  Do not store First Line
		    		 */
		    		if(lineNo==0) {
		    			
		    			/*
		    			 * Data should be in following sequence only else we will not accept this file
		    			 */
		    			
		    			if(!row.equals("id,name,position,symbol,weight,timestamp")) {
		    				
		    				throw new IOException("not the desired csv");
		    				
		    			}
		    		  
		    		} else {
		    			
		    			elements.add(row);
		    		}
		    		
		    		lineNo++;
		        
		    	}
		    	
		    	elementService.batch(elements);
		    	
		    	/*
		    	 * Change Upload File status
		    	 */
	    		upload.setStatus(FileStatus.COMPLETE);
		    	uploadRepository.save(upload);

		    	
	    	} catch (Exception e) {
	    		
	    		/*
		    	 * Change Upload File status
		    	 */
	    		upload.setStatus(FileStatus.FAILED);
		    	uploadRepository.save(upload);

	    		System.out.println(e.getMessage());
	    		
	    	} finally {
	    		
	    		if(sc != null) {
	    			
	    			sc.close();
	    			
	    		}
	    		
	    	}	  		
		}
	}


	public void importFile(MultipartFile file) throws IllegalStateException, IOException {
	
		/*
		 *  Getting current directory and adding current Timestamp to filename to avoid overwriting of files with same name
		 */
		
		this.fileName = 
				System.getProperty("user.dir") 
				+ "/../" 
				+ String.valueOf(System.currentTimeMillis()) 
				+ "_" 
				+ file.getOriginalFilename();
		
		/*
		 * Storing File on disk
		 */
		file.transferTo(new File (fileName));
		
		/*
		 * Storing it in DB
		 */
		Upload upload = new Upload(fileName);
		
		uploadRepository.save(upload);
	}

	public List<Upload> list() {
		return uploadRepository.findAll();
	}
	
}