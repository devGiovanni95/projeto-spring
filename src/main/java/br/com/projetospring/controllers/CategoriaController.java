package br.com.projetospring.controllers;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

//    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity< List<Categoria>> findAll() {
        List<Categoria> list = categoriaService.findAll();
        return ResponseEntity.ok().body(list);
    }


    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = categoriaService.find(id);
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

//    @PutMapping/*Com esse mapeamento nao deu certo tive que passar direto no metodo requestMappiing*/
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
        obj.setId(id);/*Garantia de que vai chamar o id correto*/
        obj = categoriaService.update(obj);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Categoria> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();/*Volta resposta sem conteudo*/
    }

}
