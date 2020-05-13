package com.afri.fileupload;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		UploadedFile uploadedFile = null;
		UploadedFile file = (UploadedFile) uploadedFile;

		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "uploadForm.salectFile", "Please select a file.");
		}

	}

}
