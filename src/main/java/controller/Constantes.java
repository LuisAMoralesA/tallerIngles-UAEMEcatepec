/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author anton
 */
public class Constantes {
    //Implementa constantes de inicio de sesión
    public static final int DATO_INCORRECTO = 1;
    public static final int USUARIO_NO_ENCONTRADO = 2;
    public static final int ACCESO_CONCEDIDO = 3; 
    
    //Constantes para hacer la conexion a la base de datos
    public static final String NOMBRE_DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL_DB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
    public static final String NOMBRE_USUARIO = "nbUser";
    public static final String PASSWORD_USUARIO = "123456";
    
    //Constantes para los titulos de los JSP
    public static final String TITULO_ACTUALIZAR_INFORMACION = "Modificar Informacion Personal";
    public static final String TITULO_ACTUALIZAR_USUARIO = "Modificar Informacion de Acceso";
    public static final String TITULO_AGREGAR_INFORMACION = "Agregar Registro";
    public static final String TITULO_ASIGNAR_CALIFICACIONES = "Asignacion de Calificaciones";
    public static final String TITULO_CALENDARIO = "Calendario de Mensualidades";
    public static final String TITULO_LISTA_ADMIN = "Lista de Administradores";
    public static final String TITULO_LISTA_ALUMNOS = "Lista de Alumnos";
    public static final String TITULO_LISTA_DOCUMENTOS = "Documentos Disponibles";
    public static final String TITULO_LISTA_GRUPOS = "Lista de Grupos Existentes";
    public static final String TITULO_LISTA_TEACHERS = "Lista de Profesores";
    public static final String TITULO_LOGIN_ADMIN = "Inicio de Sesion | Administradores";
    public static final String TITULO_LOGIN_ALUMNO = "Inicio de Sesion | Alumnos";
    public static final String TITULO_LOGIN_TEACHER = "Inicio de Sesion | Profesores";
    public static final String TITULO_MENU_ADMIN = "Control Escolar de Administradores";
    public static final String TITULO_MENU_ALUMNO = "Control Escolar de Alumnos";
    public static final String TITULO_MENU_TEACHER = "Control Escolar de Profesores";
    public static final String TITULO_SEGUIMIENTO_PAGO = "Seguimiento de Pago de Alumno";
    public static final String TITULO_SESION_EXPIRADA = "Sesion Expirada";
    public static final String TITULO_VISTA_CALIFICACIONES = "Menu de Calificaciones";
}
