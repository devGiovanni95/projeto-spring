package br.com.projetospring.services;

import br.com.projetospring.dto.CategoriaDTO;
import br.com.projetospring.dto.ClienteDTO;
import br.com.projetospring.entities.Categoria;
import br.com.projetospring.entities.Cliente;
import br.com.projetospring.exceptions.DataIntegrityException;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public Cliente update(Cliente obj){
        find(obj.getId());/*Iremos reaproveitar o metodo criado na categoria find para verificar se id realmente s=existe se nao existir ele lancara uma exceção */
        return clienteRepository.save(obj);
    }

    public void delete(Integer id){
        find(id);
        try {
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        throw new UnsupportedOperationException();
    }
}
