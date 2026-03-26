/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Category dentro de la base de datos. 
 * @author Luis Morales
 */
public class Category {
    private int id_category;
    private String description_category;

    public Category(int id_category, String description_category) {
        this.id_category = id_category;
        this.description_category = description_category;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getDescription_category() {
        return description_category;
    }

    public void setDescription_category(String description_category) {
        this.description_category = description_category;
    }
    
}
