package br.com.nicoletti.loto.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicoletti.loto.beans.dto.ApostaAutomaticaDTO;
import br.com.nicoletti.loto.beans.dto.ProgressaoLinearTO;
import br.com.nicoletti.loto.services.GeradorService;

@RestController
@RequestMapping(path = "/gerador")
public class GeradorController {

	@Autowired
	private GeradorService service;

	@PostMapping("/aleatorio")
	public ResponseEntity<List<Set<Integer>>> createAuto(@RequestBody ApostaAutomaticaDTO dto) {
		List<Set<Integer>> out = service.createAuto(dto);
		return ResponseEntity.ok().body(out);
	}

	@PostMapping("/tendencia")
	public ResponseEntity<List<ProgressaoLinearTO>> report(@RequestBody ApostaAutomaticaDTO dto) {
		List<ProgressaoLinearTO> out = service.tendencia(dto);
		return ResponseEntity.ok().body(out);
	}

	// Novas implementações
	@GetMapping("/stats")
	public Map<String, List<Integer>> getHotAndColdNumbers(
			@RequestParam(value = "tipoJogo", defaultValue = "MEGASENA") String tipoJogo,
			@RequestParam(value = "initialDate", defaultValue = "") String initialDate) {
		return service.calculateHotAndColdNumbers(tipoJogo, initialDate);
	}

}
