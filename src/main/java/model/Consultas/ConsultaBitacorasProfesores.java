/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Consultas;

/**
 * Es una representacion de la consulta ConsultaBitacorasProfesores dentro de la base de datos.
 * <br>
 * Funciona para imprimir las Bitacoras de Asistencia de Profesores del TEI. 
 * @author Luis Morales
 */
public class ConsultaBitacorasProfesores {
    private int id_teacher;
    private String apellido_paterno_teacher;
    private String apellido_materno_teacher;
    private String nombre_teacher;
    private int id_group;
    private int id_grade;
    private String description_grade;
    private int level_group;
    private int id_category_group;
    private String description_category;
    private String classroom_teacher;

    public ConsultaBitacorasProfesores(int id_teacher, String apellido_paterno_teacher, String apellido_materno_teacher, String nombre_teacher, int id_group, int id_grade, String description_grade, int level_group, int id_category_group, String description_category, String classroom_teacher) {
        this.id_teacher = id_teacher;
        this.apellido_paterno_teacher = apellido_paterno_teacher;
        this.apellido_materno_teacher = apellido_materno_teacher;
        this.nombre_teacher = nombre_teacher;
        this.id_group = id_group;
        this.id_grade = id_grade;
        this.description_grade = description_grade;
        this.level_group = level_group;
        this.id_category_group = id_category_group;
        this.description_category = description_category;
        this.classroom_teacher = classroom_teacher;
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

    public String getClassroom_teacher() {
        return classroom_teacher;
    }

    public void setClassroom_teacher(String classroom_teacher) {
        this.classroom_teacher = classroom_teacher;
    }
    
}
