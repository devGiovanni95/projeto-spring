package br.com.projetospring.controllers;

import br.com.projetospring.entities.Cliente;
import br.com.projetospring.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {
        Cliente obj = clienteService.busca(id);
        return ResponseEntity.ok().body(obj);
    }

}
