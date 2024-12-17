package br.com.nicoletti.loto.beans.dto;

import br.com.nicoletti.loto.beans.entities.DezenaSorteadaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DezenaSorteadaDTO {

    private Long id;
    private Integer dezena;
    private Integer ordem;

    public DezenaSorteadaDTO(DezenaSorteadaEntity entity) {
        this.id = entity.getId();
        this.dezena = entity.getDezena();
        this.ordem = entity.getOrdem();
    }
}
