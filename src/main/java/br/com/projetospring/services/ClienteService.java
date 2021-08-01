package br.com.projetospring.services;

import br.com.projetospring.entities.Cliente;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private ClienteRepository clienteRepository;

    public Cliente find(Integer id){
        Optional <Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }
}
