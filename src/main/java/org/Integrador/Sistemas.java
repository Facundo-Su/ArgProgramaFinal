package org.Integrador;


import java.util.List;

public class Sistemas {

    private List<Alumno> alumnos;
    private  List<Materia> materias;

    public boolean validador(String nombre, String materia){
        boolean resultado=true;

        return resultado;
    }

    public Sistemas(List<Alumno> alumnos, List<Materia> materias) {
        this.alumnos = alumnos;
        this.materias = materias;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void agregarAlumnos(Alumno alumnos) {
        this.alumnos.add(alumnos);
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(Materia materias) {
        this.materias.add(materias);
    }
}
