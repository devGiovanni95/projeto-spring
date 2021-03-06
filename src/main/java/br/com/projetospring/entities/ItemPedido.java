package br.com.projetospring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Entity
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    /*Para que ignore essa classe e ela nao seja serializada pelas outras e tenha uma relacao sicicla*/
    @JsonIgnore
    @EmbeddedId
    /*Id do itemPedidoPk e uma chave composta */
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedido() {

    }

    //    public ItemPedido(ItemPedidoPK id, Double desconto, Integer quantidade, Double preco) {
//        this.id = id;
//        this.desconto = desconto;
//        this.quantidade = quantidade;
//        this.preco = preco;
//    }
//
    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        id.setPedido(pedido);
        id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public double getSubTotal() {
        return (preco - desconto) * quantidade;
    }

    /*Para que nao haja relacao sicicla iremos ignorar o metodo getPedido*/
    @JsonIgnore
    public Pedido getPedido() {
        return id.getPedido();
    }

    /*Para definir um pedido*/
    public void setPedido(Pedido pedido) {
        id.setPedido(pedido);
    }

    //    @JsonIgnore
    public Produto getProduto() {
        return id.getProduto();
    }

    /*Para definir um produto*/
    public void setProduto(Produto produto) {
        id.setProduto(produto);
    }

    public ItemPedidoPK getId() {
        return id;
    }

    public void setId(ItemPedidoPK id) {
        this.id = id;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido)) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        final StringBuilder sb = new StringBuilder();
        sb.append(getProduto().getNome());
        sb.append(" ,Quantidade: ");
        sb.append(getQuantidade());
        sb.append(", Pre??o unit??rio: ");
        sb.append(nf.format(getPreco()));
        sb.append(", SubTotal: ");
        sb.append(nf.format(getSubTotal()));
        sb.append('\n');
        return sb.toString();
    }
}
