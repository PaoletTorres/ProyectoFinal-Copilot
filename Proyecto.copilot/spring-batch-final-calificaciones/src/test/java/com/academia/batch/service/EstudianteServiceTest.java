package com.academia.batch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    void contarAprobadosDebeDevolver2() {
        EstudianteEntity aprobado1 = new EstudianteEntity();
        aprobado1.setPromedio(95.0);

        EstudianteEntity aprobado2 = new EstudianteEntity();
        aprobado2.setPromedio(70.0);

        EstudianteEntity reprobado = new EstudianteEntity();
        reprobado.setPromedio(69.9);

        when(estudianteRepository.findAll()).thenReturn(List.of(aprobado1, aprobado2, reprobado));

        long totalAprobados = estudianteService.contarAprobados();

        assertEquals(2, totalAprobados);
    }
}
