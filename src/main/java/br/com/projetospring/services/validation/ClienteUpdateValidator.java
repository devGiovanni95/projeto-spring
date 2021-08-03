package br.com.projetospring.services.validation;

import br.com.projetospring.controller_exception.FieldMessage;
import br.com.projetospring.dto.ClienteDTO;
import br.com.projetospring.dto.ClienteNewDTO;
import br.com.projetospring.entities.Cliente;
import br.com.projetospring.enums.TipoCliente;
import br.com.projetospring.repositories.ClienteRepository;
import br.com.projetospring.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;//para obter parametro da uri para acessar id do cliente

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));//pegar o id no final da requisicao

        List<FieldMessage> list = new ArrayList<>();


        Cliente pesquisaEmail = clienteRepository.findByEmail(objDto.getEmail());
        /*se- pesquisa de email for diferente de null E o id do email for diferente do Id passado para atualização faca */
        if (pesquisaEmail != null && !pesquisaEmail.getId().equals(uriId)){
            list.add(new FieldMessage("email", "Email já existente! "));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}

