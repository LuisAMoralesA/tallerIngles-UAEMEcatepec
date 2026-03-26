/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;

/**
 * Es una representacion de la consulta ConsultaTeacher dentro de la base de datos. 
 * @author Luis Morales
 */
public class ConsultaTeacher {
    private int id_teacher;
    private String apellido_paterno_teacher;
    private String apellido_materno_teacher;
    private String nombre_teacher;
    private Object fecha_nacimiento_teacher;
    private String telefono_teacher;
    private String email_teacher;
    private int id_group_teacher;
    private String status_teacher;
    private String rango;
    private int id_user;
    private String nom_user;
    private String classroom;

    public ConsultaTeacher(int id_teacher, String apellido_paterno_teacher, String apellido_materno_teacher, 
            String nombre_teacher, Object fecha_nacimiento_teacher, String telefono_teacher, String email_teacher, 
            int id_group_teacher, String status_teacher, String rango, int id_user, String nom_user, String classroom) {
        this.id_teacher = id_teacher;
        this.apellido_paterno_teacher = apellido_paterno_teacher;
        this.apellido_materno_teacher = apellido_materno_teacher;
        this.nombre_teacher = nombre_teacher;
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
        this.telefono_teacher = telefono_teacher;
        this.email_teacher = email_teacher;
        this.id_group_teacher = id_group_teacher;
        this.status_teacher = status_teacher;
        this.rango = rango;
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.classroom = classroom;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
    
    
    public int getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getApellido_paterno_teacher() {
        return apellido_paterno_teacher;
    }

    public void setApellido_paterno_teacher(String apellido_paterno_teacher) {
        this.apellido_paterno_teacher = apellido_paterno_teacher;
    }

    public String getApellido_materno_teacher() {
        return apellido_materno_teacher;
    }

    public void setApellido_materno_teacher(String apellido_materno_teacher) {
        this.apellido_materno_teacher = apellido_materno_teacher;
    }

    public String getNombre_teacher() {
        return nombre_teacher;
    }

    public void setNombre_teacher(String nombre_teacher) {
        this.nombre_teacher = nombre_teacher;
    }

    public Object getFecha_nacimiento_teacher() {
        return fecha_nacimiento_teacher;
    }

    public void setFecha_nacimiento_teacher(Object fecha_nacimiento_teacher) {
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
    }

    public String getTelefono_teacher() {
        return telefono_teacher;
    }

    public void setTelefono_teacher(String telefono_teacher) {
        this.telefono_teacher = telefono_teacher;
    }

    public String getEmail_teacher() {
        return email_teacher;
    }

    public void setEmail_teacher(String email_teacher) {
        this.email_teacher = email_teacher;
    }

    public int getId_group_teacher() {
        return id_group_teacher;
    }

    public void setId_group_teacher(int id_group_teacher) {
        this.id_group_teacher = id_group_teacher;
    }

    public String getStatus_teacher() {
        return status_teacher;
    }

    public void setStatus_teacher(String status_teacher) {
        this.status_teacher = status_teacher;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }
    
}
