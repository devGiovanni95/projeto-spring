package br.com.projetospring.services;

import br.com.projetospring.entities.Estado;
import br.com.projetospring.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll(){
        return  estadoRepository.findAllByOrderByNome();
    }
}
