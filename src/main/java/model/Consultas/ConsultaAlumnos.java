/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;

/**
 * Es una representacion de la consulta ConsultaAlumnos dentro de la base de datos. 
 * @author Luis Morales
 */
public class ConsultaAlumnos {
    private int id_student;
    private String apellido_paterno_student;
    private String apellido_materno_student;
    private String nombre_student;
    private String nom_user;
    private String telefono1_student;
    private String telefono2_student;
    private int id_teacher_student;
    private String rango;
    private int id_user_student;
    private int id_payment_student;
    private int id_report_student;
    private String email_student;
    private boolean sale_solo;
    private Object fecha_nacimiento_teacher;

    public ConsultaAlumnos(int id_student, String apellido_paterno_student, String apellido_materno_student, String nombre_student, String nom_user, String telefono1_student, String telefono2_student, int id_teacher_student, String rango, int id_user_student, int id_payment_student, int id_report_student, String email_student, boolean sale_solo, Object fecha_nacimiento_teacher) {
        this.id_student = id_student;
        this.apellido_paterno_student = apellido_paterno_student;
        this.apellido_materno_student = apellido_materno_student;
        this.nombre_student = nombre_student;
        this.nom_user = nom_user;
        this.telefono1_student = telefono1_student;
        this.telefono2_student = telefono2_student;
        this.id_teacher_student = id_teacher_student;
        this.rango = rango;
        this.id_user_student = id_user_student;
        this.id_payment_student = id_payment_student;
        this.id_report_student = id_report_student;
        this.email_student = email_student;
        this.sale_solo = sale_solo;
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
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

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getTelefono1_student() {
        return telefono1_student;
    }

    public void setTelefono1_student(String telefono1_student) {
        this.telefono1_student = telefono1_student;
    }

    public String getTelefono2_student() {
        return telefono2_student;
    }

    public void setTelefono2_student(String telefono2_student) {
        this.telefono2_student = telefono2_student;
    }

    public int getId_teacher_student() {
        return id_teacher_student;
    }

    public void setId_teacher_student(int id_teacher_student) {
        this.id_teacher_student = id_teacher_student;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public int getId_user_student() {
        return id_user_student;
    }

    public void setId_user_student(int id_user_student) {
        this.id_user_student = id_user_student;
    }

    public int getId_payment_student() {
        return id_payment_student;
    }

    public void setId_payment_student(int id_payment_student) {
        this.id_payment_student = id_payment_student;
    }

    public int getId_report_student() {
        return id_report_student;
    }

    public void setId_report_student(int id_report_student) {
        this.id_report_student = id_report_student;
    }

    public String getEmail_student() {
        return email_student;
    }

    public void setEmail_student(String email_student) {
        this.email_student = email_student;
    }

    public boolean isSale_solo() {
        return sale_solo;
    }

    public void setSale_solo(boolean sale_solo) {
        this.sale_solo = sale_solo;
    }

    public Object getFecha_nacimiento_teacher() {
        return fecha_nacimiento_teacher;
    }

    public void setFecha_nacimiento_teacher(Object fecha_nacimiento_teacher) {
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
    }       
}