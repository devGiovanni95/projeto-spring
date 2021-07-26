package br.com.projetospring.controllers;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {
        Categoria obj = categoriaService.busca(id);
        return ResponseEntity.ok().body(obj);
    }

}
