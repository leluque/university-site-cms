package br.com.fatecmogidascruzes.email;

import java.io.IOException;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${email.from}")
	private String from;
	@Value("${email.accountid}")
	private String accountid;
	@Value("${email.authtoken}")
	private String authtoken;

	@Autowired
	public EmailServiceImpl() {
		super();
	}

	private void sendEmail(String from, String to, String subject, String content) {
		Thread sendMailThread = new Thread() {
			public void run() {
				try {

					String json = "{" + "   \"fromAddress\": \"" + from + "\"," + "   \"toAddress\": \"" + to + "\","
							+ "   \"subject\": \"" + subject + "\"," + "   \"content\": \"" + content + "\","
							+ "   \"mailFormat\": \"html\"" + "}";
					System.out.println(json);
					json = "{" + 
							"   \"fromAddress\": \"documentacao@fatecmogidascruzes.com.br\"," + 
							"   \"toAddress\": \"leandro.luque@gmail.com\"," + 
							"   \"subject\": \"Email - Always and Forever\"," + 
							"   \"content\": \"Email can never be dead. The most neutral and effective way, that can be used for one to many and two way communication.\"," + 
							"   \"mailFormat\": \"html\"" + 
							"}			";
					
					StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpPost request = new HttpPost("https://mail.zoho.com/api/accounts/" + accountid + "/messages");
					System.out.println("https://mail.zoho.com/api/accounts/" + accountid + "/messages");
					System.out.println(authtoken);
					request.setHeader("Authorization", authtoken);
					request.setHeader("Content-Type", "application/json");
					request.setEntity(entity);

					HttpResponse response = httpClient.execute(request);
					System.out.println(response.getStatusLine().getStatusCode());

					Scanner s = new Scanner(response.getEntity().getContent()).useDelimiter("\\A");
					String result = s.hasNext() ? s.next() : "";
					System.out.println(result);
				} catch (Exception error) {
					error.printStackTrace();
				}
			}
		};

		sendMailThread.start();
	}

	@Override
	public void sendRecoveryPasswordEMail(String to, String hash)
			throws SAXException, IOException, ParserConfigurationException, MessagingException {

		String content = "<!DOCTYPE HTML>" + "<html lang=\"pt\">" + "  <head>"
				+ "    <meta charset=\"utf-8\" />" + "  </head>" + "  <body bgcolor='#F1F1F1'>"
				+ "	<div style=\"background-color: white; margin: 0 auto 0 auto; padding: 1em; text-align: center;\">"
				+ "	  <p>Você solicitou a recuperação da sua senha de acesso ao site da Fatec Mogi das Cruzes.<br /><a href=\"https://fatecmogidascruzes.com.br/changePassword?hash="
				+ hash
				+ "\">Clique aqui para alterar a sua senha.</a><br /><em>Este link estará disponível por 24 horas.</em><br /><br />Caso você não tenha solicitado a alteração de senha, apenas desconsidere este e-mail.</p>"
				+ "	  <br />" + "	  <footer>2018 - Fatec Mogi das Cruzes</footer>" + "	</div>" + ""
				+ "</body>" + "</html>";
		sendEmail(from, to, "Recuperação de Senha - Fatec Mogi das Cruzes", content);
	}

	@Override
	public void sendContactEmail(String from, String phone, String name, String messageContent)
			throws SAXException, IOException, ParserConfigurationException, MessagingException {

		String content = "<!DOCTYPE HTML>" + "<html lang=\"pt\">" + "  <head>"
				+ "    <meta charset=\"utf-8\" />" + "  </head>" + "  <body bgcolor='#F1F1F1'>"
				+ "	<div style=\"background-color: white; margin: 0 auto 0 auto; padding: 1em; text-align: center;\">"
				+ "	  <p>Um contato foi recebido pelo site da Fatec Mogi das Cruzes:</p>" + "	  <p>Nome: " + name
				+ "</p>" + "	  <p>E-mail: " + from + "</p>" + "	  <p>Telefone: " + phone + "</p>"
				+ "	  <p>Mensagem: " + messageContent + "</p>"
				+ "	  <footer>2018 - Fatec Mogi das Cruzes</footer>" + "	</div>" + "" + "</body>"
				+ "</html>";

		sendEmail(from, "f184acad@cps.sp.gov.br", "Contato via site - Fatec Mogi das Cruzes", content);

	}

	@Override
	public void sendRequestEMail(String email, String name, String protocol, String requestSituation,
			String documentType) throws SAXException, IOException, ParserConfigurationException, MessagingException {

		String content = "<!DOCTYPE HTML>" + "<html lang=\"pt\">" + "  <head>"
				+ "    <meta charset=\"utf-8\" />" + "  </head>" + "  <body bgcolor='#F1F1F1'>"
				+ "	<div style=\"background-color: white; margin: 0 auto 0 auto; padding: 1em; text-align: center;\">"
				+ "	  <p>" + name
				+ ", você solicitou um documento por meio do site da Fatec Mogi das Cruzes. Estamos enviando este e-mail para lhe informar sobre a situação da sua solicitação.</p>"
				+ "	  <p>Protocolo: " + protocol + "</p>" + "	  <p>Tipo de documento: " + documentType
				+ "</p>" + "	  <p>Situação: " + requestSituation + "</p>"
				+ "	  <footer>2018 - Fatec Mogi das Cruzes</footer>" + "	</div>" + "" + "</body>"
				+ "</html>";

		sendEmail(from, email, "Solicitação de Documento - Fatec Mogi das Cruzes", content);

	}

}
