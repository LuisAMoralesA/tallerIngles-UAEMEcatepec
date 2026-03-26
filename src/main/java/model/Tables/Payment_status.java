/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Payment_status dentro de la base de datos. 
 * @author Luis Morales
 */
public class Payment_status {
    private int id_status;
    private String description_status;

    public Payment_status(int id_status, String description_status) {
        this.id_status = id_status;
        this.description_status = description_status;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public String getDescription_status() {
        return description_status;
    }

    public void setDescription_status(String description_status) {
        this.description_status = description_status;
    }
    
    
}
