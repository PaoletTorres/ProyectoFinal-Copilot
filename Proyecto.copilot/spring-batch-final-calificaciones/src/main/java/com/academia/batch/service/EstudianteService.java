package com.academia.batch.service;

import com.academia.batch.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de consultas sobre estudiantes procesados.
 */
@Service
public class EstudianteService {

	private final EstudianteRepository estudianteRepository;

	public EstudianteService(EstudianteRepository estudianteRepository) {
		this.estudianteRepository = estudianteRepository;
	}

	/**
	 * Cuenta cuántos estudiantes tienen promedio mayor o igual a 70.
	 *
	 * @return total de estudiantes aprobados
	 */
	public long contarAprobados() {
		final double umbralAprobacion = 70;

		return estudianteRepository.findAll()
				.stream()
				.filter(estudiante -> estudiante.getPromedio() >= umbralAprobacion)
				.count();
	}

	public long contarReprobados() {
		return estudianteRepository.findAll().stream()
				.filter(estudiante -> estudiante.getPromedio() < 70)
				.count();
	}
}
