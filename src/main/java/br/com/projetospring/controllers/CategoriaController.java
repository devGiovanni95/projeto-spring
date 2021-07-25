package br.com.projetospring.controllers;

import br.com.projetospring.domain.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @GetMapping
    public List<Categoria> listar(){

        Categoria categoria1 = new Categoria(1,"Informática");
        Categoria categoria2 = new Categoria(2,"Escritório");

        List<Categoria> lista = new ArrayList<>();
        lista.add(categoria1);
        lista.add(categoria2);
        return lista;
    }

}
