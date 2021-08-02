package br.com.projetospring.entities;

import br.com.projetospring.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private Integer tipo;
//    private TipoCliente tipo;

//    @JsonManagedReference/*A classe cliente pode referenciar com os endereços*/
//    @OneToMany(mappedBy = "cliente")
    /*Definindo que quando apagar ele ira apagar os respectivos enderecos tbem
    so precisamos fazer isso(Quando nao tem um pedido associado pois caso tenha
     um pedsido associado ele lanca uma excessao automaticamente pois nao foi
      feita essa autorização no mapeamento de clientes para pedidos )*/
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "TELEFONE")
    private Set<String> telefones = new HashSet<>();

//    @JsonBackReference/*Para nao permitir que a classe cliente serialize e mostre os pedidos*/
    @JsonIgnore
    /*Criando uma refencia de cliente com um pedido*/
    @OneToMany(mappedBy = "cliente")/*Mapeado por*/
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {

    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        /*Operador ternario*/
        this.tipo = (tipo==null) ? null : tipo.getCod();/*Se o tipo for igual a nulo atribuir nulo para campo, caso contrario adicione o codigo para ele*/
//        this.tipo = tipo.getCod();/*Precisamos habilitar a opcao de na hora de atualizar uma informacao a possibilidade de ter o valor null*/
//        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    //    public TipoCliente getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(TipoCliente tipo) {
//        this.tipo = tipo;
//    }
//
    public TipoCliente getTipo() {
        return TipoCliente.toEnum(tipo);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo.getCod();
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
