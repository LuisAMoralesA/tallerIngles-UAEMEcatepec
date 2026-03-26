/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Grupos dentro de la base de datos. 
 * @author Luis Morales
 */
public class Grupos {
    private int id_group;
    private int id_grade;
    private int level_group;
    private int id_category_group;
    private String classroom_group;

    public Grupos(int id_group, int id_grade, int level_group, int id_category_group, String classroom_group) {
        this.id_group = id_group;
        this.id_grade = id_grade;
        this.level_group = level_group;
        this.id_category_group = id_category_group;
        this.classroom_group = classroom_group;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public int getId_grade() {
        return id_grade;
    }

    public void setId_grade(int id_grade) {
        this.id_grade = id_grade;
    }

    public int getLevel_group() {
        return level_group;
    }

    public void setLevel_group(int level_group) {
        this.level_group = level_group;
    }

    public int getId_category_group() {
        return id_category_group;
    }

    public void setId_category_group(int id_category_group) {
        this.id_category_group = id_category_group;
    }

    public String getClassroom_group() {
        return classroom_group;
    }

    public void setClassroom_group(String classroom_group) {
        this.classroom_group = classroom_group;
    }
    
    
}
