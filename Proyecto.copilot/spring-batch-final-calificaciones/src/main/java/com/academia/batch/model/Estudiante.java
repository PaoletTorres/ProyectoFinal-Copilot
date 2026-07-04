package com.academia.batch.model;

/**
 * Clase modelo Estudiante (POJO) para procesamiento en Spring Batch.
 * Campos requeridos:
 * - nombre (String)
 * - grupo (String)
 * - nota1 (double)
 * - nota2 (double)
 * - nota3 (double)
 * - promedio (double)
 * 
 * Requisitos:
 * - Constructor vacío explícito.
 * - Métodos Getters y Setters para todos los campos.
 * - Método toString() que muestre únicamente: nombre, grupo y promedio.
 */
public class Estudiante {
    
    private String nombre;
    private String grupo;
    private double nota1;
    private double nota2;
    private double nota3;
    private double promedio;

    // Constructor vacío explícito
    public Estudiante() {
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    // Método toString() que muestra únicamente: nombre, grupo y promedio
    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", grupo='" + grupo + '\'' +
                ", promedio=" + promedio +
                '}';
    }
}