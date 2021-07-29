package br.com.projetospring.entities;

import java.io.Serializable;
import java.util.Date;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date instante;

    private Pagamento pagamento;
    private Cliente cliente;

    /*como a relacao de pedido e unidirecional
    para endereco nao precisamos criar um retorno ou mapeamento  para ele na classe de endereco
    nem instanciar uma classe de pedido
    */
    private Endereco enderecoDeEntrega;

    public Pedido(){

    }

    public Pedido(Integer id, Date instante, Pagamento pagamento, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instante = instante;
        this.pagamento = pagamento;
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
}
