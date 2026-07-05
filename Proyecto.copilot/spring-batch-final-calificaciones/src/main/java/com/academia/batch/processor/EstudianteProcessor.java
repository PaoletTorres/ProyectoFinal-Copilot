package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EstudianteProcessor implements ItemProcessor<Estudiante, Estudiante> {

    @Override
    public Estudiante process(Estudiante estudiante) {
        double promedio = (estudiante.getNota1() + estudiante.getNota2() + estudiante.getNota3()) / 3;
        estudiante.setPromedio(promedio);
        return estudiante;
    }
}
