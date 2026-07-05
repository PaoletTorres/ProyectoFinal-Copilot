package com.academia.batch.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte;
import org.junit.jupiter.api.Test;

class ReporteEstudianteProcessorTest {

    @Test
    void debeAsignarAprobadoCuandoPromedioEs70() {
        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Ana");
        estudiante.setGrupo("A1");
        estudiante.setPromedio(70.0);

        EstudianteReporte reporte = processor.process(estudiante);

        assertEquals("APROBADO", reporte.getEstado());
        assertEquals("Ana", reporte.getNombre());
        assertEquals("A1", reporte.getGrupo());
        assertEquals(70.0, reporte.getPromedio(), 0.0001);
    }

    @Test
    void debeAsignarReprobadoCuandoPromedioEs69_9() {
        ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();
        Estudiante estudiante = new Estudiante();
        estudiante.setPromedio(69.9);

        EstudianteReporte reporte = processor.process(estudiante);

        assertEquals("REPROBADO", reporte.getEstado());
    }
}
