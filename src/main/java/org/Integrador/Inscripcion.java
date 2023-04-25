package org.Integrador;

import java.util.Date;

public class Inscripcion {

    private Alumno alumno;
    private Materia materia;
    private Date fecha = new Date();

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;

    }

    public Inscripcion() {
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean aprobada(){

        return this.materia.puedeCursar(this.alumno);
    }

}
