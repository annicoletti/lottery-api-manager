package br.com.nicoletti.loto.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardErrorDTO {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
