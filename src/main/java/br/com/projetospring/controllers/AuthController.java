package br.com.projetospring.controllers;

import br.com.projetospring.dto.EmailDTO;
import br.com.projetospring.security.JWTUtil;
import br.com.projetospring.security.UserSS;
import br.com.projetospring.services.AuthService;
import br.com.projetospring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto){
        authService.sendNewPassord(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
