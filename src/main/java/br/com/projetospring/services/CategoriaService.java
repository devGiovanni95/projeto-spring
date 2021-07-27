package br.com.projetospring.services;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private CategoriaRepository categoriaRepository;

    public Categoria busca(Integer id){
        Optional <Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Categoria.class.getName()
        ));
    }
}
