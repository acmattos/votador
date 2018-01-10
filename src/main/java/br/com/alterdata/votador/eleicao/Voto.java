package br.com.alterdata.votador.eleicao;

import br.com.alterdata.votador.funcionario.Funcionario;
import br.com.alterdata.votador.recurso.Recurso;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author acmattos
 */
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Voto {
   @Id
   @SequenceGenerator(name = "voto_id_seq",
                      sequenceName = "voto_id_seq",
                      allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,
                   generator = "voto_id_seq")
   private Long id;
   private String comentario;
   @Convert(converter = LocalDateTimeConverter.class)
   private LocalDateTime datahora;
   
   @ManyToOne
   @JoinColumn(name = "funcionario_id")
   private Funcionario funcionario;

   @ManyToOne
   @JoinColumn(name = "recurso_id")
   private Recurso recurso;
   
}
