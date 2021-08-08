package br.com.projetospring.services;

import br.com.projetospring.entities.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;
/*Criar o corpo da mensagem e suas definicoes*/

public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")//pegando da classe properties
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

     protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));//pra da a mesma data e hora do servidor
         sm.setText(obj.toString());
        return sm;
    }
}
