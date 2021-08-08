package br.com.projetospring.config;

import br.com.projetospring.services.DBService;
import br.com.projetospring.services.EmailService;
import br.com.projetospring.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
//quando usamos o bean o metodo vai estar disponivel como COMPONENTE
// no sistema e se em outra classe instanciar o Emailservice o Spring
// vai procurar um componente BEAN que devolva uma inc=stancia deste cara
    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
