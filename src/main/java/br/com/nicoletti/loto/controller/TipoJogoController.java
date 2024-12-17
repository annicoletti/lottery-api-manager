package br.com.nicoletti.loto.controller;

import br.com.nicoletti.loto.beans.dto.TipoJogoDTO;
import br.com.nicoletti.loto.services.TipoJogoService;
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

@RestController
@RequestMapping(path = "/tipojogo")
public class TipoJogoController {

    @Autowired
    private TipoJogoService service;

    @GetMapping
    public ResponseEntity<Page<TipoJogoDTO>> list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                  @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                  @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<TipoJogoDTO> out = service.list(pageRequest);
        return ResponseEntity.ok().body(out);
    }

    @PostMapping
    public ResponseEntity<TipoJogoDTO> create(@RequestBody TipoJogoDTO dto) {
        TipoJogoDTO out = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(out.getId()).toUri();
        return ResponseEntity.created(uri).body(out);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoJogoDTO> get(@PathVariable Long id) {
        TipoJogoDTO out = service.get(id);
        return ResponseEntity.ok().body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoJogoDTO> update(@PathVariable Long id, @RequestBody TipoJogoDTO dto) {
        TipoJogoDTO out = service.update(id, dto);
        return ResponseEntity.ok().body(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoJogoDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
