/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;


/**
 * Es una representacion de la consulta ConsultaAdmin dentro de la base de datos. 
 * @author Luis Morales
 */
public class ConsultasAdmin {
    private int id_admin;
    private String apellido_paterno_admin;
    private String apellido_materno_admin;
    private String nombre_admin;
    private String rango;
    private int id_user;
    private String nom_user;
    private String telefono_admin;
    private String email_admin;
    private Object fecha_nacimiento_admin;

    public ConsultasAdmin(int id_admin, String apellido_paterno_admin, String apellido_materno_admin, String nombre_admin, String rango, int id_user, String nom_user, String telefono_admin, String email_admin, Object fecha_nacimiento_admin) {
        this.id_admin = id_admin;
        this.apellido_paterno_admin = apellido_paterno_admin;
        this.apellido_materno_admin = apellido_materno_admin;
        this.nombre_admin = nombre_admin;
        this.rango = rango;
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.telefono_admin = telefono_admin;
        this.email_admin = email_admin;
        this.fecha_nacimiento_admin = fecha_nacimiento_admin;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getApellido_paterno_admin() {
        return apellido_paterno_admin;
    }

    public void setApellido_paterno_admin(String apellido_paterno_admin) {
        this.apellido_paterno_admin = apellido_paterno_admin;
    }

    public String getApellido_materno_admin() {
        return apellido_materno_admin;
    }

    public void setApellido_materno_admin(String apellido_materno_admin) {
        this.apellido_materno_admin = apellido_materno_admin;
    }

    public String getNombre_admin() {
        return nombre_admin;
    }

    public void setNombre_admin(String nombre_admin) {
        this.nombre_admin = nombre_admin;
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

    public String getTelefono_admin() {
        return telefono_admin;
    }

    public void setTelefono_admin(String telefono_admin) {
        this.telefono_admin = telefono_admin;
    }

    public String getEmail_admin() {
        return email_admin;
    }

    public void setEmail_admin(String email_admin) {
        this.email_admin = email_admin;
    }

    public Object getFecha_nacimiento_admin() {
        return fecha_nacimiento_admin;
    }

    public void setFecha_nacimiento_admin(Object fecha_nacimiento_admin) {
        this.fecha_nacimiento_admin = fecha_nacimiento_admin;
    }
    
    
}
