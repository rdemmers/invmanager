package nl.roydemmers.invmanager.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import nl.roydemmers.invmanager.dao.InventoryDao;
import nl.roydemmers.invmanager.dao.InventoryLogItemDao;
import nl.roydemmers.invmanager.objects.GlobalPref;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.InventoryLogItem;

@Service("uploadService")
public class UploadService {
	
	@Autowired
	protected ServletContext context;

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
