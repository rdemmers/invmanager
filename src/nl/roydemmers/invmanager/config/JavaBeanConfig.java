package nl.roydemmers.invmanager.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import nl.roydemmers.invmanager.service.PreferenceService;

@Configuration
public class JavaBeanConfig {
	

//TODO afmaken
	@Bean
	public JavaMailSender getJavaMailSender(PreferenceService preferenceService) {
		
	    JavaMailSender mailSender = new JavaMailSenderImpl();
	    ((JavaMailSenderImpl) mailSender).setHost(preferenceService.getPreferenceValue("mailhost"));
	    ((JavaMailSenderImpl) mailSender).setPort(Integer.parseInt(preferenceService.getPreferenceValue("mailport")));
	     
	    ((JavaMailSenderImpl) mailSender).setUsername(preferenceService.getPreferenceValue("mailusername"));
	    ((JavaMailSenderImpl) mailSender).setPassword(preferenceService.getPreferenceValue("mailpassword"));
	     
	    Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
	    props.put("mail.transport.protocol", preferenceService.getPreferenceValue("mailprotocol"));
	    props.put("mail.smtp.auth", preferenceService.getPreferenceValue("mailauth"));
	    props.put("mail.smtp.starttls.enable", preferenceService.getPreferenceValue("mailtls"));
	    props.put("mail.debug", preferenceService.getPreferenceValue("maildebug"));
	     
	    return mailSender;
	}


}
