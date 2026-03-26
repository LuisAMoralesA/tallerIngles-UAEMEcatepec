/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Grade dentro de la base de datos. 
 * @author Luis Morales
 */
public class Grade {
    private int id_grade;
    private String description_grade;

    public Grade(int id_grade, String description_grade) {
        this.id_grade = id_grade;
        this.description_grade = description_grade;
    }

    public int getId_grade() {
        return id_grade;
    }

    public void setId_grade(int id_grade) {
        this.id_grade = id_grade;
    }

    public String getDescription_grade() {
        return description_grade;
    }

    public void setDescription_grade(String description_grade) {
        this.description_grade = description_grade;
    }
    
    
}

