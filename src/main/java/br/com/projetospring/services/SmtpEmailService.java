package br.com.projetospring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    //vai instanciar o mailSender com todos os dados e chaves
    // do servico de email definido no application.dev/prod.properties
    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Enviando Email.......");
        mailSender.send(msg);//para enviar a mensagem
        LOG.info("Email enviado");

    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOG.info("Enviando Email.......");
        javaMailSender.send(msg);//para enviar a mensagem
        LOG.info("Email enviado");
    }
}
