package org.Integrador;

import java.util.ArrayList;
import java.util.List;

public class Materia {
    private String nombre;
    private ArrayList<String> correlativas;

    public Materia(String nombre) {
        this.nombre = nombre;
    }
    public Materia(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getMaterias() {
        return correlativas;
    }

    public void setMaterias(ArrayList<String> correlativas) {
        this.correlativas =correlativas;
    }
    public boolean puedeCursar(Alumno alumno){

        return alumno.getMateriasAprobadas().containsAll(this.correlativas);
    }
}
