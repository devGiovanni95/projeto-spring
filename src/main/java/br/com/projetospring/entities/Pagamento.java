package br.com.projetospring.entities;

import br.com.projetospring.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
/*Colocamos a classe como abstrata para que so consiguemes usar as subclasses */
public abstract class Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id/*Nao colocamos para gerar id automaticamente pois precisamos gerar o mesmo id do pedido*/
    private Integer id;
    private Integer estado;

    @JsonBackReference/*Nao permitir que a classe Pagamento chame a classe pedido para que nao haja relacao sicicla */
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId/*Garantir que seja o mesmo id*/
    private Pedido pedido;

    public Pagamento(){

    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        super();
        this.id = id;
        this.estado = estado.getCod();
        this.pedido = pedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCod();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento)) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(getId(), pagamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
