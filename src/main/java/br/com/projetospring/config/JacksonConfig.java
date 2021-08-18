package br.com.projetospring.config;

import br.com.projetospring.entities.PagamentoComBoleto;
import br.com.projetospring.entities.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JacksonConfig {

    // https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder(){
            public void configure(ObjectMapper objectMapper){
                objectMapper.registerSubtypes(PagamentoComCartao.class);
                objectMapper.registerSubtypes(PagamentoComBoleto.class);
                super.configure(objectMapper);
            }
        };
        return builder;
    }
/*
* Erro que dava
* Field javaMailSender in com.pierre.cursomc.services.AbstractEmailService required a bean of type 'org.springframework.mail.javamail.JavaMailSender' that could not be found.



The injection point has the following annotations:

- @org.springframework.beans.factory.annotation.Autowired(required=true)


* */
    @Bean
    public JavaMailSender jMS (){
        return new JavaMailSenderImpl();
    }
}
