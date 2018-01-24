package nl.roydemmers.invmanager.service;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service("uploadService")
public class UploadService {
	
	@Autowired
	protected ServletContext context;

	@Secured({"ROLE_ADMIN", "ROLE_MOD"})
	public String uploadFileReturnFileName(CommonsMultipartFile[] uploadFile) {
		
		if (uploadFile != null && uploadFile.length > 0) {
			
			for (CommonsMultipartFile aFile : uploadFile) {
				try {
					if (!aFile.getOriginalFilename().equals("")) {
						aFile.transferTo(new File(context.getRealPath("/WEB-INF/attachments/")+ "/" + aFile.getOriginalFilename()));
						
						return aFile.getOriginalFilename();
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
