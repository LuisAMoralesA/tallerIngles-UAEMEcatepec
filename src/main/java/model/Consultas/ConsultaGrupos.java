package model.Consultas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Es una representacion de la consulta ConsultaGrupos dentro de la base de datos. 
 * @author Luis Morales
 */
public class ConsultaGrupos {
    
    private int id_group;
    private int id_grade;
    private String description_grade;
    private int level_group;
    private int id_category_group;
    private String description_category;
    
    public ConsultaGrupos(int id_group, int id_grade, String description_grade, int level_group, int id_category_group, String description_category) {
        this.id_group = id_group;
        this.id_grade = id_grade;
        this.description_grade = description_grade;
        this.level_group = level_group;
        this.id_category_group = id_category_group;
        this.description_category = description_category;
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

    public String getDescription_grade() {
        return description_grade;
    }

    public void setDescription_grade(String description_grade) {
        this.description_grade = description_grade;
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

    public String getDescription_category() {
        return description_category;
    }

    public void setDescription_category(String description_category) {
        this.description_category = description_category;
    }
    
    
}
