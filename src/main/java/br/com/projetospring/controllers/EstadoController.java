package br.com.projetospring.controllers;

import br.com.projetospring.dto.CidadeDTO;
import br.com.projetospring.dto.EmailDTO;
import br.com.projetospring.dto.EstadoDTO;
import br.com.projetospring.entities.Cidade;
import br.com.projetospring.entities.Estado;
import br.com.projetospring.security.JWTUtil;
import br.com.projetospring.security.UserSS;
import br.com.projetospring.services.AuthService;
import br.com.projetospring.services.CidadeService;
import br.com.projetospring.services.EstadoService;
import br.com.projetospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/{estadoId}/cidades",method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
