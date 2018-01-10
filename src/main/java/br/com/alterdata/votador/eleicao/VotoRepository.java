package br.com.alterdata.votador.eleicao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author acmattos
 */
public interface VotoRepository extends JpaRepository<Voto, Long> {
   @Query("   select v " +
          "     from Voto v " +
          " order by v.funcionario.nome asc, v.recurso.descricao asc")
   List<Voto> findAllVotosOrdenadosPorFuncionarioErecurso();
}
