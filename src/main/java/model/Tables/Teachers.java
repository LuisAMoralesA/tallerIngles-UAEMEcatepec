/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

import java.util.Date;

/**
 * Es una representacion de la tabla Teachers dentro de la base de datos. 
 * @author Luis Morales
 */
public class Teachers {
    private int id_teacher;
    private int id_user_teacher;
    private String apellido_paterno_teacher;
    private String apellido_materno_teacher;
    private String nombre_teacher;
    private String telefono_teacher;
    private String email_teacher;
    private Object fecha_nacimiento_teacher;
    private String status_teacher;
    private int id_group_teacher;
    private String classroom_teacher;

    public Teachers(int id_teacher, int id_user_teacher, String apellido_paterno_teacher, 
            String apellido_materno_teacher, String nombre_teacher, String telefono_teacher, 
            String email_teacher, Object fecha_nacimiento_teacher, String status_teacher, int id_group_teacher,
            String classroom_teacher) {
        this.id_teacher = id_teacher;
        this.id_user_teacher = id_user_teacher;
        this.apellido_paterno_teacher = apellido_paterno_teacher;
        this.apellido_materno_teacher = apellido_materno_teacher;
        this.nombre_teacher = nombre_teacher;
        this.telefono_teacher = telefono_teacher;
        this.email_teacher = email_teacher;
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
        this.status_teacher = status_teacher;
        this.id_group_teacher = id_group_teacher;
        this.classroom_teacher = classroom_teacher;
    }

    public int getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(int id_teacher) {
        this.id_teacher = id_teacher;
    }

    public int getId_user_teacher() {
        return id_user_teacher;
    }

    public void setId_user_teacher(int id_user_teacher) {
        this.id_user_teacher = id_user_teacher;
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

    public Object getFecha_nacimiento_teacher() {
        return fecha_nacimiento_teacher;
    }

    public void setFecha_nacimiento_teacher(Date fecha_nacimiento_teacher) {
        this.fecha_nacimiento_teacher = fecha_nacimiento_teacher;
    }

    public String getStatus_teacher() {
        return status_teacher;
    }

    public void setStatus_teacher(String status_teacher) {
        this.status_teacher = status_teacher;
    }

    public int getId_group_teacher() {
        return id_group_teacher;
    }

    public void setId_group_teacher(int id_group_teacher) {
        this.id_group_teacher = id_group_teacher;
    }

    public String getClassroom_teacher() {
        return classroom_teacher;
    }

    public void setClassroom_teacher(String classroom_teacher) {
        this.classroom_teacher = classroom_teacher;
    }
    
}
