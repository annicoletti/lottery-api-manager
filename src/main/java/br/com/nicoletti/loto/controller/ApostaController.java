package br.com.nicoletti.loto.controller;

import br.com.nicoletti.loto.beans.dto.ApostaDTO;
import br.com.nicoletti.loto.beans.dto.NumerosVerificadoTO;
import br.com.nicoletti.loto.services.ApostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/apostas")
public class ApostaController {

    @Autowired
    private ApostaService service;

    @GetMapping
    public ResponseEntity<Page<ApostaDTO>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                @RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ApostaDTO> out = service.list(pageRequest);
        return ResponseEntity.ok().body(out);
    }

    @PostMapping
    public ResponseEntity<ApostaDTO> create(@RequestBody ApostaDTO dto) {
        ApostaDTO out = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(out.getId()).toUri();
        return ResponseEntity.created(uri).body(out);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApostaDTO> get(@PathVariable Long id) {
        ApostaDTO out = service.get(id);
        return ResponseEntity.ok().body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApostaDTO> update(@PathVariable Long id, @RequestBody ApostaDTO dto) {
        ApostaDTO out = service.update(id, dto);
        return ResponseEntity.ok().body(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApostaDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * CONFERE APOSTA
     **/

    @PostMapping("/confere/historico/{tipoJogo}")
    public ResponseEntity<List<NumerosVerificadoTO>> confereNumeroApostaHistorico(@PathVariable String tipoJogo,
                                                                                  @RequestBody List<Set<Integer>> numeros) {
        List<NumerosVerificadoTO> out = service.confereNumeroApostaHistorico(tipoJogo, numeros);
        return ResponseEntity.ok().body(out);
    }


}
