package com.home.ans.holidays.service.impl;

import com.home.ans.holidays.configuration.db.ConfigEntity;
import com.home.ans.holidays.configuration.db.ConfigRepository;
import com.home.ans.holidays.entity.OfferEntity;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.entity.TuiOfferEntity;
import com.home.ans.holidays.service.NotificationService;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private static final String MAIL_RECIPIENTS = "mail.recipients";

    @Value("${mail.smtp}")
    private String SMTP_SERVER;

    @Value("${mail.smtp.port}")
    private String SMTP_PORT;

    @Value("${mail.login}")
    private String LOGIN;

    @Value("${mail.password}")
    private String PASSWORD;

    @Value("${mail.sender}")
    private String EMAIL_SENDER_ADDRESS;

    private ConfigRepository configRepository;

    @Override
    public void notifyAboutRainbowOffer(RainbowOfferEntity offer) {
        Properties prop = prepareProperties();

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(prop, auth);
        Message msg = new MimeMessage(session);

        try (SMTPTransport t = (SMTPTransport) session.getTransport("smtp")) {
            msg.setFrom(new InternetAddress(EMAIL_SENDER_ADDRESS));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(updateRecipients(), false));
            msg.setSubject(prepareSubject(offer));
            msg.setText(prepareMessageText(offer));
            msg.setSentDate(new Date());

            t.connect(SMTP_SERVER, LOGIN, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAboutTuiOffer(TuiOfferEntity offer) {
        Properties prop = prepareProperties();

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(prop, auth);
        Message msg = new MimeMessage(session);

        try (SMTPTransport t = (SMTPTransport) session.getTransport("smtp")) {
            msg.setFrom(new InternetAddress(EMAIL_SENDER_ADDRESS));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(updateRecipients(), false));
            msg.setSubject(prepareSubject(offer));
            msg.setText(prepareMessageText(offer));
            msg.setSentDate(new Date());

            t.connect(SMTP_SERVER, LOGIN, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //todo: change method parameter from entity to dto!
    private String prepareMessageText(OfferEntity offer) {

        return "Lokalizacja: " + offer.getDestination() +
                "\nhotel: " + offer.getHotelName() + " " + offer.getHotelStandard() + "*" +
                "\nwylot: " + offer.getDepartureDateAndTime() +
                "\ndlugość pobytu: " + offer.getDuration() +
                "\ncena: " + offer.getDiscountPerPersonPrice() +
                "\nwyżywienie: " + offer.getBoardType() +
                "\nURL: " + offer.getOfferUrl();
    }

    private String prepareSubject(OfferEntity offer) {
        return String.format("NOWA OFERTA! %s %s* na %d dni, za %d PLN", offer.getDestination(), offer.getHotelStandard().toString(), offer.getDuration(), offer.getDiscountPerPersonPrice());
    }

    private Properties prepareProperties() {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", SMTP_PORT);
        return prop;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(LOGIN, PASSWORD);
        }
    }

    @Deprecated
    private String updateRecipients() {
        log.error("updateRecipients method invoked in class %s. Create new method to take mails from alert criteria repository", this.getClass().getSimpleName());
        return configRepository.findById(MAIL_RECIPIENTS).orElse(ConfigEntity.builder().value("").build()).getValue();
    }

    @Autowired
    public void setConfigRepository(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
}
