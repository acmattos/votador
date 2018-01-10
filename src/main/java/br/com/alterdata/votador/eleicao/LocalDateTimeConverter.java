package br.com.alterdata.votador.eleicao;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author acmattos
 */
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
   @Override
   public Timestamp convertToDatabaseColumn(LocalDateTime datahora) {
      return datahora != null ? Timestamp.valueOf(datahora) : null;
   }
   @Override
   public LocalDateTime convertToEntityAttribute(Timestamp bdDatahora) {
      return bdDatahora != null ? bdDatahora.toLocalDateTime() : null;
   }
}
