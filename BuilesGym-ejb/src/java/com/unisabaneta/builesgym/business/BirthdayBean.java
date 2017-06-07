/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.business;

import com.unisabaneta.builesgym.dao.ClientFacade;
import com.unisabaneta.builesgym.entity.Client;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Andres
 */
@Singleton
@LocalBean
public class BirthdayBean {

    @Inject
    private ClientFacade clientFacade;

    /**
     * cada mes recorre los clientes registrados y envia mensajes de texto a los
     * que están cumpliendo años en el mes
     */
    // @Schedule(month = "1,12")
    @Schedule(minute = "*", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void monitorSendEmailBirthDay() {
        List<Client> clientList = clientFacade.findAll();
        if (clientList != null && !clientList.isEmpty()) {
            clientList.stream().forEach(p -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
                String monthClient = dateFormat.format(p.getBirthdayClient());
                String monthSystem = dateFormat.format(new Date());
                if (monthClient.equals(monthSystem)) {
                    try {
                        System.out.println("Enviar a" + p.getEmailClient() + dateFormat.format(p.getBirthdayClient()));
//                        sendMail("gymbuiles@gmail.com", p.getEmailClient(), "Cumpleaños",
//                                "Hola" + "\n" + "" + "\n" + p.getNameClient() + "\n"
//                                + "Este mes cumples años, recibe un caluroso saludo por parte de GYM Builes.");
//
//                        sendMail("gymbuiles@gmail.com", "gymbuiles@gmail.com", "Cumpleaños",
//                                "Hola" + "\n" + "" + "\n" + p.getNameClient() + "\n"
//                                + "Este mes cumples años, recibe un caluroso saludo por parte de GYM Builes.");
                    } catch (Exception e) {
                        System.out.println("Error" + e);
                    }
                }
            });
        }
    }

    private String mailSMTPServer;
    private String mailSMTPServerPort;
    private String mailSenha;

    public void sendMail(String from, String to, String subject, String message) throws MessagingException {
        Properties pro = new Properties();

        mailSMTPServer = "smtp.gmail.com";
        mailSMTPServerPort = "465";
        mailSenha = "senha";

        pro.put("mail.transport.protocol", "smtp");
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", mailSMTPServer);
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.user", from);
        pro.put("mail.debug", "true");
        pro.put("mail.smtp.port", mailSMTPServerPort);
        pro.put("mail.smtp.socketFactory.port", mailSMTPServerPort);
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        pro.put("mail.smtp.socketFactory.fallback", "false");

        SimpleAuth auth = null;
        auth = new SimpleAuth(from, mailSenha);

        Session session = Session.getDefaultInstance(pro, auth);
        session.setDebug(true);

        Message msg = new MimeMessage(session);

        try {
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            msg.setFrom(new InternetAddress(from));

            msg.setSubject(subject);

            //msg.setContent(multiParte);
            msg.setContent(message, "text/HTML");

            //msg.setContent(message, "text/area");
        } catch (Exception e) {
            System.out.println(">> Error: Completar mensaje");
        }

        Transport tr;

        try {
            tr = session.getTransport("smtp");

            tr.connect(mailSMTPServer, from, mailSenha);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            System.out.println(">> Error: Envio Mensaje " + e);
        }
    }
}

class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = "Builes2017";
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

//
//    public static void send(String from, String password, String to, String sub, String msg) {
//        //Get properties object    
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.starttls.enable","true"); 
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//        //get Session   
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//        //compose message    
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(sub);
//            message.setText(msg);
//            //send message  
//            Transport.send(message);
//            System.out.println("message sent successfully");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
