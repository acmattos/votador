package br.com.alterdata.votador.recurso;

import br.com.alterdata.votador.eleicao.Voto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author acmattos
 */
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude="votos")
@NoArgsConstructor
@AllArgsConstructor
public class Recurso {
   @Id
   @SequenceGenerator(name = "recurso_id_seq",
      sequenceName = "recurso_id_seq",
      allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "recurso_id_seq")
   private Long id;
   @NotNull
   private String descricao;
   @OneToMany(mappedBy = "recurso", fetch = FetchType.EAGER)
   private List<Voto> votos;
   @Transient
   private long totalDeVotos;
   
   public Recurso(Long id, String descricao, long totalDeVotos){
      this.id = id;
      this.descricao = descricao;
      this.totalDeVotos = totalDeVotos;
   }
}
