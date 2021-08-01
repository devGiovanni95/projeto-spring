package br.com.projetospring.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;

    /*Agora vamos permitir que a classe pedido serialize para exibir os pagamentos quando a classe chamar e nao permitir que a classe pagamento chame a classe pedido*/
//    @JsonManagedReference
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "pedido")/*Para evitar erro na hora de salvar de entidade transiente*/
    private Pagamento pagamento;


    /*vamos serializar para que nao haja relacao sicicla onde permitiremos que o pedido mostre o cliente mas nao permitiremos que o cliente mostre o cliente*/
//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    /*como a relacao de pedido e unidirecional
    para endereco nao precisamos criar um retorno ou mapeamento  para ele na classe de endereco
    nem instanciar uma classe de pedido
    */

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;


    /*Set para nao ter itens repetidos*/
    @OneToMany(mappedBy = "id.produto")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(){

    }

    public Pedido(Integer id, Date instante,/* Pagamento pagamento,*/ Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instante = instante;
//        this.pagamento = pagamento;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido pedido = (Pedido) o;
        return getId().equals(pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
