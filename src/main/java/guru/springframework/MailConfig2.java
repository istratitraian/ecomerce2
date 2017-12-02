package guru.springframework;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Configuration
//@PropertySource("classpath:properties/mail.properties")
public class MailConfig2 {

    private String protocol;
    private String host;
    private int port;
    private boolean auth;
    private boolean starttls;
    private String senderEmail;
    private String senderPassword;

    public MailConfig2(
            @Value("${mail.protocol}") String protocol,
             @Value("${mail.smtp.host}") String host,
             @Value("${mail.smtp.port}") int port,
             @Value("${mail.smtp.auth}") boolean auth,
             @Value("${mail.smtp.starttls.enable}") boolean starttls,
             @Value("${sender.email}") String sender,
             @Value("${sender.password}") String password) {

        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.starttls = starttls;
        this.senderEmail = sender;
        this.senderPassword = password;

        System.out.println("MailConfig2() @Value email : " + sender + ", password : " + password);
    }

//	@Bean(name = "javMailSender")
//	public JavaMailSender javMailSender() {
//
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		Properties mailProperties = new Properties();
//		mailProperties.put("mail.smtp.auth", auth);
//		mailProperties.put("mail.smtp.starttls.enable", starttls);
//		mailSender.setJavaMailProperties(mailProperties);
//		mailSender.setHost(host);
//		mailSender.setPort(port);
//		mailSender.setProtocol(protocol);
//		mailSender.setUsername(senderEmail);
//		mailSender.setPassword(senderPassword);
//		return mailSender;
//
//	}
}
