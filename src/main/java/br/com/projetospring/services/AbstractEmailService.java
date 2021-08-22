package br.com.projetospring.services;

import br.com.projetospring.entities.Cliente;
import br.com.projetospring.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
/*Criar o corpo da mensagem e suas definicoes*/

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")//pegando da classe properties
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));//pra da a mesma data e hora do servidor
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        /*se a mensagem html nao for enviada enviaremos a normal mesmo*/
        try {
            MimeMessage mimeMessage = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mimeMessage);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    //    /*para ser usado em uma subclasse*/
    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(obj.getCliente().getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Pedido confirmado! Código: " + obj.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplatePedido(obj), true);
        return mimeMessage;
    }
//    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
//        mmh.setTo(obj.getCliente().getEmail());
//        mmh.setFrom(sender);
//        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
//        mmh.setSentDate(new Date(System.currentTimeMillis()));
//        mmh.setText(htmlFromTemplatePedido(obj), true);
//        return mimeMessage;
//    }
    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass );
        sendEmail(sm);
    }

    private SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha ");
        sm.setSentDate(new Date(System.currentTimeMillis()));//pra da a mesma data e hora do servidor
        sm.setText("Nova Senha: " + newPass);
        return sm;

    }

}
