/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller.struct;
import model.Tables.*;
import model.Consultas.*;
import java.util.ArrayList;

/**
 * Interfaz que permite crear funciones prototipo para las operaciones en la base de datos
 * @author Luis Morales
 */
public interface BaseDatosStruct {
    //Parte 1: Metodos de insersión de datos
    public void insertarAdministrador(Admin_school admin);
    public void insertarCategoria(Category category);
    public void insertarEstatusDePago(Payment_status status);
    public int  insertarSeguimientoDePago(Payment pay);
    public void insertarEstudiante(Students student);
    public void insertarGrupos(Grupos group);
    public void insertarNivel(Grade grade);
    public void insertarMes(Pay_simbology symbol);
    public int  insertarReporteCalificaciones(Report report);
    public void insertarTeacher(Teachers teacher);
    public void insertarUsuario(Users user);
    
    //Parte 2: Metodos de Actualización de datos
    public void actualizarAdministrador(Admin_school admin);
    public void actualizarCategorias(Category category);
    public void actualizarEstatusDePago(Payment_status status);
    public void actualizarSeguimientoDePago(Payment pay);
    public void actualizarEstudiante(Students student);
    public void cambiarEstudianteDeGrupo(Students student);
    public void actualizarGrupos(Grupos group);
    public void actualizarNivel(Grade grade);
    public void actualizarCalendario(Pay_simbology symbol);
    public void actualizarReporteCalificaciones(Report report);
    public void actualizarTeacher(Teachers teacher);
    public void actualizarUsuario(Users user);
    public int  actualizarListas();
    
    //Parte 3: Visualizacion de tablas
    public ArrayList<Users> obtenerUsuarios();
    public ArrayList<Teachers> obtenerTeachers();
    public ArrayList<Students> obtenerEstudiantes();
    public ArrayList<Admin_school> obtenerAdministrador();
    public ArrayList<Report> obtenerCalificaciones();
    public ArrayList<Payment_status> obtenerEstatus();
    public ArrayList<Payment> obtenerSeguimiento();
    public ArrayList<Pay_simbology> obtenerCalendario();
    public ArrayList<Grupos> obtenerGrupos();
    public ArrayList<Grade> obtenerNivel();
    public ArrayList<Category> obtenerCategorias();
    
    //Parte 4: Visualizacion de tablas (Registos unicos)
    public ArrayList<Users> obtenerUsuario(String usuario);
    public ArrayList<Teachers> obtenerTeacher(int id_user);
    public ArrayList<Admin_school> obtenerAdministrador(int id_user);
    public ArrayList<Students> obtenerEstudiante(int id_user);
    public ArrayList<Report> obtenerCalificaciones(int id);
    public String obtenerEstatus(int id);
    public ArrayList<Payment> obtenerSeguimiento(int id);
    public String obtenerPeriodo(String mes);
    public ArrayList<Pay_simbology> obtenerCalendario(String periodo);
    public ArrayList<Pay_simbology> obtenerDatosDeMes(int id_month);
    public ArrayList<Grupos> obtenerGrupos(int id);
    public String obtenerNivel(int id);
    public String obtenerCategorias(int id);
    public int inicioSesion(String user, String password, String rol);
    
    //Parte 5: Visualizacion de consultas
    public ArrayList<ConsultasAdmin> obtenerDatosAdministradores();
    public ArrayList<ConsultaAlumnos> obtenerDatosAlumnos();
    public ArrayList<ConsultaGrupos> obtenerDatosGrupos();
    public ArrayList<ConsultaTeacher> obtenerDatosProfesores();
    
    //Parte 6: Visualizacion de Consultas (registros especificos)
    public ArrayList<ConsultasAdmin> obtenerDatosAdministrador(String usuario);
    public ArrayList<ConsultaAlumnos> obtenerDatosAlumno(String usuario);
    public ArrayList<ConsultaGrupos> obtenerDatosGrupo(int id_grupo);
    public ArrayList<ConsultaTeacher> obtenerDatosProfesores(String usuario);
    
    //Parte 7: Operaciones de concatenacion
    public String concatenarInfoGrupo(int id_grupo);
    public String concatenarDatosGrupo(int id_prof);
    public String concatenarDatosProfesor(int id_prof);
    public String concatenarDatosAlumno(int id_student);
    public String concatenarDatosAdministrador(int id_admin);
    
    //Parte 8: Operaciones de conteo
    public int conteoAlumnos();
    public int conteoAlumnos(int id_teacher);
    public int conteoProfesores();
    public int conteoMeses(String periodoActual);
    
    //Parte 9: Operaciones para generacion de documentos
    public ArrayList<ConsultaBitacorasAlumnos> obtenerBitacorasDeAlumnos(int id_teacher);
    public ArrayList<ConsultaPagos> obtenerListasSeguimientoDePago(int id_teacher);
    public ArrayList<ConsultaBitacorasProfesores> obtenerBitacoraProfesores();
    public ArrayList<ConsultaCalificaciones> obtenerCalificacionesPorGrupo(int id_teacher);
    
    //Parte 10: Operaciones de eliminacion de datos
    public void eliminarUsuario(int id_user);
    public void eliminarAdministrador(int id_admin);
    public void eliminarTeacher(int id_teacher);
    public void eliminarAlumno(int id_student);
    public void eliminarListaCalificaciones(int id_report);
    public void eliminarListaDePago(int id_payment);
    public void eliminarGrupo(int id_group);
    
    //Parte 11: Operaciones de desvinculacion de tablas
    public void desvincularProfesores(int id_group);
    public void desvincularAlumnos(int id_teacher);
}
