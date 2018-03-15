package nl.roydemmers.invmanager.service;

import java.util.List;

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
import nl.roydemmers.invmanager.objects.Order;
import nl.roydemmers.invmanager.objects.Product;
import nl.roydemmers.invmanager.objects.Supplier;
import nl.roydemmers.invmanager.objects.User;

/**
 * @author Roy Demmers
 *
 */
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

	/** Sends an email with specified text to the owner of the application
	 * 
	 * @param emailMessage Text that should be mailed
	 * @param user User responsable for sending the issue
	 */
	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void createIssueEmail(EmailMessage emailMessage, User user) {

		String body = emailMessage.getMailBody();

		body += "\n\n" + user.getUsername();
		body += "\n" + user.getEmail();
		body += "\n" + user.getAuthority();

		String subject = "Issue report: " + user.getUsername();
		String target = "foo@bar.com";

		this.sendMail(target, subject, body, "");
		
	}

	/**Create a notifcation about a product having a lower stock than the minimum.
	 * 
	 * Currently not being used
	 * 
	 * @param product Product that should be included in the e-mail
	 */
	@Async
	@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MOD"})
	public void createNotificationMail(Product product) {
		Supplier supplier = product.getSupplier();

		String body = "";

		body += "Het volgende product is onder het minimum gekomen:\n\n";
		body += "Productnaam: " + product.getName();
		body += "\nProductcode: " + product.getBarcode();
		body += "\nHuidige Voorraad: " + product.getCurrentStock();
		body += "\nMinimum Voorraad: " + product.getStockMinimum();
		body += "\nPrijs: " + product.getPrice();
		body += "\n\nLeverancier: " + product.getSupplier().getName();
		body += "\nContactpersoon: " + supplier.getContact();
		body += "\nBestel Email: " + supplier.getOrderMail();
		body += "\nVragen Email: " + supplier.getQuestionMail();
		body += "\nTelefoonnummer: " + supplier.getPhone();
		body += "\n\n\nBericht voor de leverancier:";
		body += "\n\n" + product.getBarcode();
		body += "\t" + product.getName();
		body += "\t" + product.getOrderMetric();

		String target = preferenceService.getPreferenceValue("mailtarget");
		String subject = "[INVENTORY-ERP][Minimum] Product: " + product.getName();
		String attachment = product.getAttachment();

		this.sendMail(target, subject, body, attachment);

	}
	
	/**Add an attachment to the Message
	 * 
	 * @param attachment String reference to the attachment
	 * @param helper Mimemessage target for the attachment
	 */
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

	/**Send an e-mail from the adress set in the database
	 * @see nl.roydemmers.invmanager.config.JavaBeanConfig
	 * 
	 * @param target Target mail adress 
	 * @param subject	Subject of the mail
	 * @param body Body of the mail
	 * @param attachment String reference to the attachment
	 */
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
	
	
	/**Takes a List<Order> and creates an e-mail with the orders to the supplier
	 * 
	 * @param orderList List of orders that should be sent to the supplier
	 */
	public void createBatchOrderMail(List<Order> orderList) {
		Supplier supplier = orderList.get(0).getProductId().getSupplier();

		String body = "";

		body += "Wij zouden graag de volgende producten willen bestellen:\n\n";
		
		for(Order o : orderList) {
			Product product = o.getProductId();
			
			body += "Productnaam: " + product.getName();
			body += "\nProductcode: " + product.getBarcode();
			body += "\nHoeveelheid: " + o.getQuantityMultiplier();
		}
		
		body += "\nMet vriendelijke groeten";

		String target = "gibiguku@emailure.net";
		String subject = "Bestelling voor " + supplier.getName();
		String attachment = "";

		this.sendMail(target, subject, body, attachment);

	}
	
}
