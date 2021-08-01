package br.com.projetospring.services;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.exceptions.DataIntegrityException;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private CategoriaRepository categoriaRepository;

    public Categoria find(Integer id){
        Optional <Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Categoria.class.getName()
        ));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);/*Setando para que garanta que esta criandop um objeto novo e que nao esta atualizando um dado*/
        return categoriaRepository.save(obj);
    }

    public Categoria update(Categoria obj){
        find(obj.getId());/*Iremos reaproveitar o metodo criado na categoria find para verificar se id realmente s=existe se nao existir ele lancara uma exceção */
        return categoriaRepository.save(obj);
    }

    public void delete(Integer id){
        find(id);
        try {
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }
}
