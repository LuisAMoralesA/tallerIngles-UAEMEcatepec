/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Users dentro de la base de datos. 
 * @author Luis Morales
 */
public class Users {
    private int id_user;
    private String nom_user;
    private String password;
    private String rango;

    public Users(int id_user, String nom_user, String password, String rango) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }
    
    
}
