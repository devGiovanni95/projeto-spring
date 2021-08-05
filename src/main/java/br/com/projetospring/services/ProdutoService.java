package br.com.projetospring.services;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.entities.Pedido;
import br.com.projetospring.entities.Produto;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.CategoriaRepository;
import br.com.projetospring.repositories.PedidoRepository;
import br.com.projetospring.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
        Optional <Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }

   public Page<Produto> search(String nome, List<Integer> ids, Integer page,
                                Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
//        return produtoRepository.search(nome, categorias, pageRequest);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
