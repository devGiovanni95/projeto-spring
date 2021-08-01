package br.com.projetospring.controllers;

import br.com.projetospring.dto.CategoriaDTO;
import br.com.projetospring.entities.Categoria;
import br.com.projetospring.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

//    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity< List<CategoriaDTO>> findAll() {
        List<Categoria> list = categoriaService.findAll();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction
//            @RequestParam(value = "direction", defaultValue = "DESc")String direction ---alternativa
    ) {
        Page<Categoria> list = categoriaService.findPage(page,linesPerPage,orderBy,direction);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    /*localhost:8080/categorias/page*/                                      /*pesquisa todos as categorias e mostra numa pagina se o numero for menhor que o padrao estabelecido que é 24*/
    /*localhost:8080/categorias/page?linesPerPage=3*/                       /*Pesquisa com 3 itens por pagina*/
    /*localhost:8080/categorias/page?linesPerPage=3&page=1*/                /*Pesquisa 1 segunda pagina pois a pagina inicial e page=0*/
    /*localhost:8080/categorias/page?linesPerPage=3&page=1&direction=DESC*/ /*Ordena em ordem decrecente */

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = categoriaService.find(id);
        return ResponseEntity.ok().body(obj);
    }
//
//    @PostMapping
//    @RequestMapping
//    public ResponseEntity<Void> insert(@RequestBody Categoria obj){
//        obj = categoriaService.insert(obj);
//        /*Construtor de componentes do Servlet Uri. da solicitação atual ()
//                . caminho ("/ {id}"). construir e expandir (obj. get Id ()). para Uri ();*/
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}").buildAndExpand(obj.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }

//    @PostMapping
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = categoriaService.fromDTO(objDto);
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
