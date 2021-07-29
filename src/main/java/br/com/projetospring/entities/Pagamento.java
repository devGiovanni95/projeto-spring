package br.com.projetospring.entities;

import br.com.projetospring.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id/*Nao colocamos para gerar id automaticamente pois precisamos gerar o mesmo id do pedido*/
    private Integer id;
    private EstadoPagamento estado;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId/*Garantir que seja o mesmo id*/
    private Pedido pedido;

    public Pagamento(){

    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = estado;
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return estado;
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
