package br.com.alterdata.votador.funcionario;

import br.com.alterdata.votador.eleicao.Voto;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
public class Funcionario {
   
   @Id
   @SequenceGenerator(name = "funcionario_id_seq",
                      sequenceName = "funcionario_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
                   generator = "funcionario_id_seq")
   private Long id;
   @NotNull
   private String nome;
   @NotNull
   private String email;
   @NotNull
   private String senha;
   @OneToMany(mappedBy = "funcionario", fetch = FetchType.EAGER)
   private List<Voto> votos;
   
   @PrePersist
   public void onPrePersist() {
      // TODO Tratar mudanca de senha do funcionario, provocar novaa ofuscacao
      String senhaOfuscada = new BCryptPasswordEncoder().encode(this.getSenha());
      this.setSenha(senhaOfuscada);
   }
}
