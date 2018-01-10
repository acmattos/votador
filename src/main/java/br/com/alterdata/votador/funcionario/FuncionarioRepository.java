package br.com.alterdata.votador.funcionario;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author acmattos
 */
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
   Funcionario findByEmail(String email);
}
