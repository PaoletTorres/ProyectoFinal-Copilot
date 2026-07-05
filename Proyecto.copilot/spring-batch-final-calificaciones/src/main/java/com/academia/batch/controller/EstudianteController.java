package com.academia.batch.controller;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

	private final EstudianteRepository estudianteRepository;
	private final EstudianteService estudianteService;

	public EstudianteController(EstudianteRepository estudianteRepository, EstudianteService estudianteService) {
		this.estudianteRepository = estudianteRepository;
		this.estudianteService = estudianteService;
	}

	@GetMapping("/")
	public ResponseEntity<List<EstudianteEntity>> listarTodos() {
		return ResponseEntity.ok(estudianteRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstudianteEntity> obtenerPorId(@PathVariable Long id) {
		Optional<EstudianteEntity> estudianteOpt = estudianteRepository.findById(id);
		return estudianteOpt.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/aprobados/total")
	public ResponseEntity<Map<String, Long>> totalAprobados() {
		long total = estudianteService.contarAprobados();
		return ResponseEntity.ok(Map.of("totalAprobados", total));
	}

	@PostMapping("/")
	public ResponseEntity<EstudianteEntity> crear(@RequestBody EstudianteEntity estudiante) {
		EstudianteEntity guardado = estudianteRepository.save(estudiante);
		return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstudianteEntity> reemplazar(@PathVariable Long id, @RequestBody EstudianteEntity estudiante) {
		if (!estudianteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		estudiante.setId(id);
		EstudianteEntity actualizado = estudianteRepository.save(estudiante);
		return ResponseEntity.ok(actualizado);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EstudianteEntity> actualizarGrupo(@PathVariable Long id, @RequestBody Map<String, String> cambios) {
		Optional<EstudianteEntity> estudianteOpt = estudianteRepository.findById(id);
		if (estudianteOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		EstudianteEntity estudiante = estudianteOpt.get();
		if (cambios.containsKey("grupo")) {
			estudiante.setGrupo(cambios.get("grupo"));
		}

		EstudianteEntity actualizado = estudianteRepository.save(estudiante);
		return ResponseEntity.ok(actualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (!estudianteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		estudianteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
