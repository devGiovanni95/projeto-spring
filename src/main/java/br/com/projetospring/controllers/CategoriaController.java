package br.com.projetospring.controllers;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping
    @RequestMapping
    public ResponseEntity<Void> insert(@RequestBody Categoria obj){
        obj = categoriaService.insert(obj);
        /*Construtor de componentes do Servlet Uri. da solicitação atual ()
                . caminho ("/ {id}"). construir e expandir (obj. get Id ()). para Uri ();*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
