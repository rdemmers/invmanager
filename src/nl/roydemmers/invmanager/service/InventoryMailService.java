package nl.roydemmers.invmanager.service;

import java.io.File;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.config.JavaBeanConfig;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;

@Service("inventoryMailService")
public class InventoryMailService extends AbstractService {

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
			Resource file = new ClassPathResource("com/paperfoam/inventory/mail/" + attachment);
			try {
				helper.addAttachment("attachment.png", file);
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
			}
	}



}
