package org.Integrador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static Conexion conexion = new Conexion();
    private static Scanner entrada = new Scanner(System.in);


    public static void main(String[] args) throws SQLException,JsonProcessingException {

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("==== MENÚ ====");
            System.out.println("1. agregar alumnos a base de datos");
            System.out.println("2. agregar materias a base de datos");
            System.out.println("3. inscripcion a materia");
            System.out.println("4. Salir");

            System.out.print("Ingrese una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    conexion.insertarAlumnos();
                    break;
                case 2:
                    conexion.crearMateria();
                    break;
                case 3:
                    List<Materia> materias= conexion.traerDatosMaterias();
                    List<Alumno> alumnos=conexion.traerDatosAlumno();
                    inscripcion(alumnos,materias);
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("La opción ingresada es inválida. Por favor, ingrese una opción válida.");
                    break;
            }
        } while (opcion != 4);

    }

    public static void inscripcion(List<Alumno> alumnos,List<Materia> materias){
        Inscripcion inscripcion = new Inscripcion();
        System.out.println("ingrese el nombre de alumno para la inscripcion");
        String nombre = entrada.next();
        System.out.println(" ingrese la materia que deseas inscribirte");
        String materia = entrada.next();


        boolean alumnoEncontrado = false;
        boolean materiaEncontrado = false;

        for (Alumno alumno1 : alumnos){

            if(alumno1.getNombre().equals(nombre)){
                alumnoEncontrado= true;

                for (Materia materia1: materias){
                    if(materia1.getNombre().equals(materia)){
                        materiaEncontrado=true;
                        Inscripcion validar = new Inscripcion(alumno1,materia1);
                        if(validar.aprobada()){

                            System.out.println(alumno1.getNombre()+"   "+ materia1.getNombre() +"     Aprobada");
                        }else{
                            System.out.println(alumno1.getNombre()+"   "+ materia1.getNombre() +"     Desaprobado");
                        }

                        break;
                    }

                }
            }
        }

        if (alumnoEncontrado){
            if (materiaEncontrado == false){
                System.out.println("hola  "+nombre+"  la materia  " +materia+" no ha sido encontrado");
            }
        }
        if (alumnoEncontrado == false){
            System.out.println("el alumno  "+nombre +"  no ha sido encontrado");
        }

    }





}