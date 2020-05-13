package com.afri.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.afri.eventmanager.EventCalendar;

@Controller
@RequestMapping("/")
public class UploadFileController {
	String newFileName = "Events01.csv";
	@Autowired
	FileValidator fileValidator;

//	 @RequestMapping("/fileUploadForm")    
//	 public ModelAndView getUploadForm(    
//	   @ModelAttribute("uploadedFile") UploadedFile uploadedFile,    
//	   BindingResult result) {    
//	  return new ModelAndView("uploadForm");    
//	 } 

//	 @RequestMapping("/fileUploadForm")    
//	 public ModelAndView getUploadForm(    
//	   @ModelAttribute("uploadedFile") UploadedFile uploadedFile,    
//	   BindingResult result) {    
//	  return new ModelAndView("uploadForm");    
//	 }  

	@RequestMapping("/fileUploadForm")
	public String getUploadForm(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {
		return "UploadForm";
	}

	@RequestMapping("/fileUpload")
	public ModelAndView fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile file = uploadedFile.getFile();
		// fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();

		if (result.hasErrors() || fileName.equals("")) {
			return new ModelAndView("UploadForm");
		}

		try {
			inputStream = file.getInputStream();

			File newFile = new File("Uploads/" + newFileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("ShowFile", "message", fileName);
	}
}
