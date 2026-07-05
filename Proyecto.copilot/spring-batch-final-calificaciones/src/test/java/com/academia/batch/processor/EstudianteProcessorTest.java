package com.academia.batch.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.academia.batch.model.Estudiante;
import org.junit.jupiter.api.Test;

class EstudianteProcessorTest {

    @Test
    void debeCalcularPromedioCorrectamente() {
        EstudianteProcessor processor = new EstudianteProcessor();
        Estudiante estudiante = new Estudiante();
        estudiante.setNota1(80.0);
        estudiante.setNota2(70.0);
        estudiante.setNota3(60.0);

        Estudiante procesado = processor.process(estudiante);

        assertEquals(70.0, procesado.getPromedio(), 0.0001);
    }
}
