/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;

/**
 * Es una representacion de la consulta ConsultaBitacorasAlumnos dentro de la base de datos. 
 * <br>
 * Funciona para imprimir las Bitacoras de Asistencia de Alumnos del TEI. 
 * @author Luis Morales
 */
public class ConsultaBitacorasAlumnos {
    private int id_student;
    private String apellido_paterno_student;
    private String apellido_materno_student;
    private String nombre_student;
    private boolean sale_solo;
    
    public ConsultaBitacorasAlumnos(int id_student, String apellido_paterno_student, String apellido_materno_student, String nombre_student, boolean sale_solo) {
        this.id_student = id_student;
        this.apellido_paterno_student = apellido_paterno_student;
        this.apellido_materno_student = apellido_materno_student;
        this.nombre_student = nombre_student;
        this.sale_solo = sale_solo;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public String getApellido_paterno_student() {
        return apellido_paterno_student;
    }

    public void setApellido_paterno_student(String apellido_paterno_student) {
        this.apellido_paterno_student = apellido_paterno_student;
    }

    public String getApellido_materno_student() {
        return apellido_materno_student;
    }

    public void setApellido_materno_student(String apellido_materno_student) {
        this.apellido_materno_student = apellido_materno_student;
    }

    public String getNombre_student() {
        return nombre_student;
    }

    public void setNombre_student(String nombre_student) {
        this.nombre_student = nombre_student;
    }

    public boolean isSale_solo() {
        return sale_solo;
    }

    public void setSale_solo(boolean sale_solo) {
        this.sale_solo = sale_solo;
    }
    
}
