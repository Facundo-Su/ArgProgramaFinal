package org.Integrador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.sql.*;
import java.util.*;

import com.google.gson.Gson;
public class Conexion {

    Connection conectar = null;

    String usuario = "root";
    String contraseña = "root";
    String bd = "argentina_programa";
    String ip = "localhost";
    String puerto = "3306";

    String ruta = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    ObjectMapper objectMapper = new ObjectMapper();
    Scanner entrada = new Scanner(System.in).useDelimiter("\n");

    public Connection estableceConexion(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conectar = DriverManager.getConnection(ruta, usuario, contraseña);

            System.out.println("se conecto correctamente");
        } catch (Exception e) {

            System.out.println("no se conecto correctamente");

        }

        return conectar;

    }

    public void insertarAlumnos() throws SQLException{
        try {
            Alumno alumno = new Alumno();
            String nombre;
            String legajo;

            System.out.println("ingrese el nombre de alumno");
            nombre=entrada.next();
            alumno.setNombre(nombre);
            do {


            System.out.println("ingrese el legajo del alumno (con al meno 5 digito)");
            legajo = entrada.next();
            alumno.setLegajo(legajo);
            }while (!legajo.matches("\\d{5,}"));

            System.out.println("ingrese cuanta materia tiene aprobado");
            int cantidad=entrada.nextInt();

            ArrayList<String> materiaAprobado = new ArrayList<>();

            String input;
            for(int i=0;i<cantidad;i++){
                System.out.println("ingrese las materia que aprobo");
                input = entrada.next();
                materiaAprobado.add(input);
            }


            String correlativasJson = new Gson().toJson(materiaAprobado);
            this.estableceConexion();
            Statement stmt = this.conectar.createStatement();
            stmt.executeUpdate("INSERT INTO alumnos VALUES ('" + nombre + "', '" + legajo + "', '" + correlativasJson + "')");
            this.cerrarConection();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "NO se conecto correctamente" + e);

        }
    }


    public void cerrarConection() throws  SQLException{
        try {
            conectar.close();
        }catch (Exception e){
        }
    }

    public List<Materia> traerDatosMaterias() throws SQLException, JsonProcessingException {

        Materia materia = new Materia();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<Materia> listaMaterias = new ArrayList<>();

        this.estableceConexion();
        Statement stmt = this.conectar.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM materia");

        while (rs.next()) {

            materia = new Materia(rs.getString("nombre"));

            String jsonText = objectMapper.writeValueAsString(rs.getString("correlativa"));

            ArrayList<String> nombreCorrelativas = objectMapper.readValue(jsonText, ArrayList.class);
            ArrayList<String> lista = new ArrayList<>();

            for (String valor : nombreCorrelativas){
                Gson gson = new Gson();
                lista = gson.fromJson(valor, ArrayList.class);
            }

            materia.setMaterias(lista);

            listaMaterias.add(materia);

        }
        this.cerrarConection();

        return listaMaterias;
    }

    public List<Alumno> traerDatosAlumno() throws SQLException, JsonProcessingException {

        Alumno alumno = new Alumno();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        List<Alumno> listaAlumno = new ArrayList<>();

        this.estableceConexion();
        Statement stmt = this.conectar.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM alumnos");

        while (rs.next()) {

            alumno = new Alumno(rs.getString("nombre"), rs.getString("legajo"));

            String jsonText = objectMapper.writeValueAsString(rs.getString("correlativa"));

            ArrayList<String> materiaAprobada = objectMapper.readValue(jsonText, ArrayList.class);
            ArrayList<String> lista = new ArrayList<>();

            for (String valor : materiaAprobada){
                Gson gson = new Gson();
                lista = gson.fromJson(valor, ArrayList.class);
            }


            alumno.setMateriasAprobadas(lista);

            listaAlumno.add(alumno);

        }
        this.cerrarConection();

        return listaAlumno;
    }



    public void crearMateria() throws SQLException {
        Scanner sc = new Scanner(System.in);

        Materia materia = new Materia();
        System.out.println("Que nombre quiere que tenga la materia?");
        String nombre = sc.next();
        materia.setNombre(nombre);

        System.out.println("Cuantas materias desea agregar a las correlativas?");
        int numero = sc.nextInt();

        System.out.println("Que materias desea agregar a las correlativas?");
        ArrayList<String> correlativas = new ArrayList<>();

        String input;
        for (int i = 0; i < numero; i++) {
            input = sc.next();
            correlativas.add(input);
        }
        String correlativasJson = new Gson().toJson(correlativas);
        this.estableceConexion();
        Statement stmt = this.conectar.createStatement();
        stmt.executeUpdate("INSERT INTO materia VALUES(\"" + nombre + "\",'" + correlativasJson + "');");
        this.cerrarConection();

    }

}
