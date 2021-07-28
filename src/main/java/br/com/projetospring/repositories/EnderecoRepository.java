package br.com.projetospring.repositories;

import br.com.projetospring.entities.Cliente;
import br.com.projetospring.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
