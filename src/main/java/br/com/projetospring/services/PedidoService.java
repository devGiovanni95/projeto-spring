package br.com.projetospring.services;

import br.com.projetospring.entities.Pedido;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private PedidoRepository pedidoRepository;

    public Pedido find(Integer id){
        Optional <Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }
}
