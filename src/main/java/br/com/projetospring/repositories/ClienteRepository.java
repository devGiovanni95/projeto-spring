package br.com.projetospring.repositories;

import br.com.projetospring.entities.Categoria;
import br.com.projetospring.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
