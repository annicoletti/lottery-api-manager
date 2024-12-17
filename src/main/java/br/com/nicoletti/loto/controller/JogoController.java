package br.com.nicoletti.loto.controller;

import br.com.nicoletti.loto.beans.dto.JogoDTO;
import br.com.nicoletti.loto.services.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/jogos")
public class JogoController {

    @Autowired
    private JogoService service;

    @GetMapping
    public ResponseEntity<Page<JogoDTO>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                              @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                              @RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<JogoDTO> out = service.list(pageRequest);
        return ResponseEntity.ok().body(out);
    }

    @PostMapping
    public ResponseEntity<JogoDTO> create(@RequestBody JogoDTO dto) {
        JogoDTO out = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(out.getId()).toUri();
        return ResponseEntity.created(uri).body(out);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoDTO> get(@PathVariable Long id) {
        JogoDTO out = service.get(id);
        return ResponseEntity.ok().body(out);
    }

    /* TIPO JOGO E CONCURSO */

    @GetMapping("/{tipoJogo}/concurso/{concurso}")
    public ResponseEntity<JogoDTO> getTipoJogoAndConcurso(@PathVariable String tipoJogo, @PathVariable Integer concurso) {
        JogoDTO out = service.getTipoJogoAndConcurso(concurso, tipoJogo);
        return ResponseEntity.ok().body(out);
    }

}
