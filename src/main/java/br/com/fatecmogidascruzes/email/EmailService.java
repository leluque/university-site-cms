package br.com.fatecmogidascruzes.email;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface EmailService {

	void sendRecoveryPasswordEMail(String to, String hash)
			throws SAXException, IOException, ParserConfigurationException, MessagingException;

	void sendContactEmail(String from, String phone, String name, String message)
			throws SAXException, IOException, ParserConfigurationException, MessagingException;

	void sendRequestEMail(String email, String name, String protocol, String requestSituation, String documentType)
			throws SAXException, IOException, ParserConfigurationException, MessagingException;

}