package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ReporteEstudianteProcessor implements ItemProcessor<Estudiante, EstudianteReporte> {

	@Override
	public EstudianteReporte process(Estudiante estudiante) {
		EstudianteReporte reporte = new EstudianteReporte();
		reporte.setNombre(estudiante.getNombre());
		reporte.setGrupo(estudiante.getGrupo());
		reporte.setPromedio(estudiante.getPromedio());

		if (estudiante.getPromedio() >= 70) {
			reporte.setEstado("APROBADO");
		} else {
			reporte.setEstado("REPROBADO");
		}

		System.out.println("Step 2 - Reporte: " + reporte);

		return reporte;
	}
}
