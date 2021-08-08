package br.com.projetospring.services;

import br.com.projetospring.entities.ItemPedido;
import br.com.projetospring.entities.PagamentoComBoleto;
import br.com.projetospring.entities.Pedido;
import br.com.projetospring.entities.Produto;
import br.com.projetospring.enums.EstadoPagamento;
import br.com.projetospring.exceptions.ObjectNotFoundException;
import br.com.projetospring.repositories.ItemPedidoRepository;
import br.com.projetospring.repositories.PagamentoRepository;
import br.com.projetospring.repositories.PedidoRepository;
import br.com.projetospring.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired//serve para intanciar automaticamente pela jpa
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;


    public Pedido find(Integer id) {
        Optional<Pedido> obj = pedidoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Tipo: " + Pedido.class.getName()
        ));
    }

    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));//setar os dados para ter uma comunicacao com o banco de dados e passar paramentros como o nome do cliente
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));//buscar no banco de dados
            ip.setPreco(ip.getProduto().getPreco());//busca o preco
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
//        System.out.println(obj);//imprimir no console o pedido
        emailService.sendOrderConfirmationEmail(obj);//precisamos informar que é uma interface e que e um servico do mock
        return obj;
    }
}
