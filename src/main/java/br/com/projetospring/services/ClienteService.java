package br.com.projetospring.services;

import br.com.projetospring.dto.CategoriaDTO;
import br.com.projetospring.dto.ClienteDTO;
import br.com.projetospring.dto.ClienteNewDTO;
import br.com.projetospring.entities.Categoria;
import br.com.projetospring.entities.Cidade;
import br.com.projetospring.entities.Cliente;
import br.com.projetospring.entities.Endereco;
import br.com.projetospring.enums.Perfil;
import br.com.projetospring.enums.TipoCliente;
import br.com.projetospring.exceptions.AuthorizationException;
import br.com.projetospring.exceptions.DataIntegrityException;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.CidadeRepository;
import br.com.projetospring.repositories.ClienteRepository;
import br.com.projetospring.repositories.EnderecoRepository;
import br.com.projetospring.security.UserSS;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired//serve para intanciar automaticamente pela jpa
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id){

        UserSS userSS = UserService.authenticated();
        if (userSS == null || !userSS.hasRole(Perfil.ADMIN) && !id.equals(userSS.getId())){
            throw  new AuthorizationException("Acesso negado");
        }

        Optional <Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);/*Setando para que garanta que esta criandop um objeto novo e que nao esta atualizando um dado*/
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }


    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return clienteRepository.save(newObj);
    }

    public void delete(Integer id){
        find(id);
        try {
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionadas");
        }
    }



    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findByEmail(String email){

        UserSS userSS = UserService.authenticated();
        if (userSS == null || !userSS.hasRole(Perfil.ADMIN) && !email.equals(userSS.getUsername())){
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = clienteRepository.findByEmail(email);
        if (obj == null ){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + userSS.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null,null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto){
        Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()),passwordEncoder.encode(objDto.getSenha()));
//        Cidade cidade = cidadeRepository.findOne(objDto.getCidadeId());
        Cidade cidade = new Cidade(objDto.getCidadeId(), null,null);
        Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(objDto.getTelefone1());

        if (objDto.getTelefone2() != null){
            cliente.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null){
            cliente.getTelefones().add(objDto.getTelefone3());
        }
        return cliente;
    }

    /*Metodo auxiliar para atualizar somente os dois campos disponivel poara atualização*/
    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){

        UserSS userSS = UserService.authenticated();
        if (userSS == null){
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

        jpgImage = imageService.cropSquare(jpgImage);//recortando a imagem passada
        jpgImage = imageService.resize(jpgImage, size);//redimensiona a imagem

        String fileName = prefix + userSS.getId()+ ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

//        URI uri = s3Service.uploadFile(multipartFile);
//        Cliente cliente = clienteRepository.findByEmail(userSS.getUsername());
//        cliente.setImageUrl(uri.toString());
//        clienteRepository.save(cliente);
//        return uri;
    }


//    Eu resolvi desta forma:
//
//    public URI uploadProfilePicture(MultipartFile multipartFile) {
//
//        UserSS user = UserService.authenticated();
//
//        if(user == null) {
//
//            throw new AuthorizationException("Acesso negado!");
//
//        }
//
//        URI uri = s3Service.uploadFile(multipartFile);
//
//        Optional<Cliente> cli = repo.findById(user.getId());
//
//        cli.get().setImageUrl(uri.toString());
//
//        repo.save(cli.get());
//
//        return uri;
//
//    }
}
