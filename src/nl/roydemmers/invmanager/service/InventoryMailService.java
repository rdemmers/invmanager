package nl.roydemmers.invmanager.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.config.JavaBeanConfig;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;


@Service("inventoryMailService")
public class InventoryMailService{
	@Autowired
	private JavaBeanConfig javaBeanConfig;
	@Autowired
	private FinancialCalculationService financialCalculationService;
	@Autowired
	protected PreferenceService preferenceService;
	@Autowired
	protected ServletContext context;
	
	
	// Creates and sends a template message with attachment if available
	// Can be used to quickly send custom emails to suppliers when stock runs low
	public void sendMessageWithAttachment(String attachment, InventoryItem inventoryItem) {

		Supplier supplier = inventoryItem.getSupplier();

		JavaMailSender mailSender = javaBeanConfig.getJavaMailSender(preferenceService);

		String text = "";

		text += "Het volgende product is onder het minimum gekomen:\n\n";
		text += "Productnaam: " + inventoryItem.getName();
		text += "\nProductcode: " + inventoryItem.getBarcode();
		text += "\nHuidige Voorraad: " + inventoryItem.getCurrentStock();
		text += "\nMinimum Voorraad: " + inventoryItem.getStockMinimum();
		text += "\nPrijs: " + inventoryItem.getPrice();
		text += "\n\nLeverancier: " + inventoryItem.getSupplier();
		text += "\nContactpersoon: " + supplier.getContact();
		text += "\nBestel Email: " + supplier.getOrderMail();
		text += "\nVragen Email: " + supplier.getQuestionMail();
		text += "\nTelefoonnummer: " + supplier.getPhone();
		text += "\n\n\nBericht voor de leverancier:";
		text += "\n\n" + inventoryItem.getBarcode();
		text += "\t" + inventoryItem.getName();
		text += "\t" + inventoryItem.getOrderQuantity();
		text += " x " + financialCalculationService.convertLongtoCurrency(inventoryItem.getPrice());

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(preferenceService.getPreferenceValue("mailtarget"));
			helper.setSubject("[INVENTORY-ERP][Minimum] Product: " + inventoryItem.getName());
			helper.setText(text);
			
			this.appendAttachment(attachment, helper);
			
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void appendAttachment(String attachment, MimeMessageHelper helper) {
		if(attachment != null) {
			FileSystemResource file = new FileSystemResource(context.getRealPath("/WEB-INF/attachments/")+ "/" + attachment);
			try {
				helper.addAttachment(file.getFilename(), file);
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
			}
	}



}
