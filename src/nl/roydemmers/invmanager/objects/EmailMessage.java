package nl.roydemmers.invmanager.objects;

public class EmailMessage {
	
	private String mailBody;
	
	public EmailMessage() {
		
	}
	
	

	public EmailMessage(String mailBody) {
		super();
		this.mailBody = mailBody;

	}



	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	
	
}
