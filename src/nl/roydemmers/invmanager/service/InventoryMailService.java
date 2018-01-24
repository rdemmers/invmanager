package nl.roydemmers.invmanager.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.config.JavaBeanConfig;
import nl.roydemmers.invmanager.objects.EmailMessage;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;

@Service("inventoryMailService")
public class InventoryMailService {
	@Autowired
	private JavaBeanConfig javaBeanConfig;
	@Autowired
	private FinancialCalculationService financialCalculationService;
	@Autowired
	protected PreferenceService preferenceService;
	@Autowired
	protected ServletContext context;

	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void createIssueEmail(EmailMessage emailMessage, User user) {

		String body = emailMessage.getMailBody();

		body += "\n\n" + user.getUsername();
		body += "\n" + user.getEmail();
		body += "\n" + user.getAuthority();

		String subject = "Issue report: " + user.getUsername();
		String target = "chibitenshin@gmail.com";

		this.sendMail(target, subject, body, "");
		
	}

	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void createNotificationMail(InventoryItem inventoryItem) {
		Supplier supplier = inventoryItem.getSupplier();

		String body = "";

		body += "Het volgende product is onder het minimum gekomen:\n\n";
		body += "Productnaam: " + inventoryItem.getName();
		body += "\nProductcode: " + inventoryItem.getBarcode();
		body += "\nHuidige Voorraad: " + inventoryItem.getCurrentStock();
		body += "\nMinimum Voorraad: " + inventoryItem.getStockMinimum();
		body += "\nPrijs: " + inventoryItem.getPrice();
		body += "\n\nLeverancier: " + inventoryItem.getSupplier().getName();
		body += "\nContactpersoon: " + supplier.getContact();
		body += "\nBestel Email: " + supplier.getOrderMail();
		body += "\nVragen Email: " + supplier.getQuestionMail();
		body += "\nTelefoonnummer: " + supplier.getPhone();
		body += "\n\n\nBericht voor de leverancier:";
		body += "\n\n" + inventoryItem.getBarcode();
		body += "\t" + inventoryItem.getName();
		body += "\t" + inventoryItem.getOrderQuantity();
		body += " x " + financialCalculationService.convertLongtoCurrency(inventoryItem.getPrice());

		String target = preferenceService.getPreferenceValue("mailtarget");
		String subject = "[INVENTORY-ERP][Minimum] Product: " + inventoryItem.getName();
		String attachment = inventoryItem.getAttachment();

		this.sendMail(target, subject, body, attachment);

	}
	
	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void appendAttachment(String attachment, MimeMessageHelper helper) {
		if (attachment != null) {
			FileSystemResource file = new FileSystemResource(context.getRealPath("/WEB-INF/attachments/") + "/" + attachment);
			try {
				helper.addAttachment(file.getFilename(), file);
			} catch (MessagingException e) {

				e.printStackTrace();
			}
		}
	}

	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void sendMail(String target, String subject, String body, String attachment) {

		JavaMailSender mailSender = javaBeanConfig.getJavaMailSender(preferenceService);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(target);
			helper.setSubject(subject);
			helper.setText(body);
			if(!attachment.isEmpty()) {
				this.appendAttachment(attachment, helper);
			}
			
			Thread.sleep(1000L);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
