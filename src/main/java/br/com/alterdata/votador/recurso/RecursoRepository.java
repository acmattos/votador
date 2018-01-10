package br.com.alterdata.votador.recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author acmattos
 */
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
   /**
    * Retorna a lista de total de votos por recursos, de forma descendente
    * (recursos mais votados emprimeiro lugar).
    * @return  Uma lista com todos os recursosA list of persons whose last name is an exact match with the given last name.
    *          If no persons is found, this method returns an empty list.
    */
   @Query(" select new br.com.alterdata.votador.recurso.RecursoVO(r.id, r.descricao, count(v.id)) " +
          "   from Recurso r " +
          " left join r.votos v " +
          " group by r.id, r.descricao " +
          " order by count(v.id) desc, r.descricao asc")
   public List<RecursoVO> findAllRecursosComTotalDeVotos();

   @Query(value = "  select distinct (r.id), r.descricao, 0 \n" +
      "    from Recurso r\n" +
      "    left outer join voto v on v.recurso_id = r.id\n" +
      "   where  v.recurso_id not in (\n" +
      "         select re.id\n" +
      "           from Recurso re\n" +
      "           left outer join voto vo on vo.recurso_id = re.id\n" +
      "          where vo.funcionario_id = :funcionarioId \n" +
      "     )\n" +
      "     or v.funcionario_id is null\n" +
      "group by r.id\n" +
      "order by r.descricao asc", nativeQuery = true)
   public List<Recurso> findAllRecursosNaoVotadosPorFuncionario(
      @Param("funcionarioId") Long funcionarioId);
}