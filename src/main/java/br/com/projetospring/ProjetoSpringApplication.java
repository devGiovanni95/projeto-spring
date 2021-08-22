package br.com.projetospring;

import br.com.projetospring.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoSpringApplication implements CommandLineRunner {

    @Autowired
    private S3Service s3Service;


    public static void main(String[] args) {
        SpringApplication.run(ProjetoSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        s3Service.uploadFile("C:\\ProjetosJava\\recursos aula spring\\imagensPerfil\\4.jpg");
    }
}
