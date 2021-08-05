package br.com.projetospring.services;

import br.com.projetospring.entities.PagamentoComBoleto;
import org.hibernate.type.CalendarType;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instanteDoPedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(calendar.getTime());
    }
}
