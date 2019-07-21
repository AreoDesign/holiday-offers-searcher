package com.home.ans.holidays.service.impl;

import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.service.NotificationService;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${mail.smtp}")
    private String SMTP_SERVER;

    @Value("${mail.login}")
    private String LOGIN;

    @Value("${mail.password}")
    private String PASSWORD;

    @Value("${mail.sender}")
    private String EMAIL_SENDER_ADDRESS;

    @Value("${mail.receiver}")
    private String EMAIL_RECEIVER_ADDRESS;

    @Override
    public void notifyAboutOffer(RainbowOfferEntity offer) {
        Properties prop = prepareProperties();

        SecurityManager security = System.getSecurityManager();
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(prop, auth);
        Message msg = new MimeMessage(session);

        try (SMTPTransport t = (SMTPTransport) session.getTransport("smtp")) {
            msg.setFrom(new InternetAddress(EMAIL_SENDER_ADDRESS));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_RECEIVER_ADDRESS, false));
            msg.setSubject(prepareSubject(offer));
            msg.setText(prepareMessageText(offer));
            msg.setSentDate(new Date());

            t.connect(SMTP_SERVER, LOGIN, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String prepareMessageText(RainbowOfferEntity offer) {
        StringBuilder sb = new StringBuilder();
        sb.append("Lokalizacja: " + offer.getLokalizacja());
        sb.append("\nhotel: " + offer.getNazwaHotelu() + " " + offer.getGwiazdkiHotelu() + "*");
        sb.append("\nwylot: " + offer.getDataWKodzieProduktu());
        sb.append("\ndlugosc pobytu: " + offer.getLiczbaDni());
        sb.append("\ncena: " + offer.getCenaAktualna());
        sb.append("\nwyzywienie: " + offer.getWyzywienie());
        sb.append("\nURL: " + offer.getOfertaUrl());

        return sb.toString();
    }

    private String prepareSubject(RainbowOfferEntity offer) {
        return String.format("NOWA OFERTA! %s %s* na %d dni, za %d PLN", offer.getLokalizacja(), offer.getGwiazdkiHotelu().toString(), offer.getLiczbaDni(), offer.getCenaAktualna());
    }

    private Properties prepareProperties() {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587");
        return prop;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(LOGIN, PASSWORD);
        }
    }
}
