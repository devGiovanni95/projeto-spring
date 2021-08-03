package br.com.projetospring.repositories;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional(readOnly = true)/*nao precisa ser fornecida como uma transacao de banco de dados deixando mais rapida a operacao*/
    Cliente findByEmail(String email);
}
