/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;

 /**
 * Es una representacion de la consulta ConsultaCalificaciones dentro de la base de datos.
 * <br>
 * Funciona para imprimir las listas de Calificaciones  del TEI. 
 * @author Luis Morales
 */
public class ConsultaCalificaciones {
    private int id_student;
    private String apellido_paterno_student;
    private String apellido_materno_student;
    private String nombre_student;
    private String nom_user;
    private int id_report;
    private double first_partial_report;
    private double second_partial_report;
    private double average_report;

    public ConsultaCalificaciones(int id_student, String apellido_paterno_student, String apellido_materno_student, String nombre_student, 
            String nom_user, int id_report, double first_partial_report, double second_partial_report, double average_report) {
        this.id_student = id_student;
        this.apellido_paterno_student = apellido_paterno_student;
        this.apellido_materno_student = apellido_materno_student;
        this.nom_user = nom_user;
        this.nombre_student = nombre_student;
        this.id_report = id_report;
        this.first_partial_report = first_partial_report;
        this.second_partial_report = second_partial_report;
        this.average_report = average_report;
    }

    public int getId_report() {
        return id_report;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public double getAverage_report() {
        return average_report;
    }

    public void setAverage_report(double average_report) {
        this.average_report = average_report;
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

    public double getFirst_partial_report() {
        return first_partial_report;
    }

    public void setFirst_partial_report(double first_partial_report) {
        this.first_partial_report = first_partial_report;
    }

    public double getSecond_partial_report() {
        return second_partial_report;
    }

    public void setSecond_partial_report(double second_partial_report) {
        this.second_partial_report = second_partial_report;
    }
    
}
