/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Tables.*;
import model.Consultas.*;
import java.util.ArrayList;
import java.util.Iterator;

//
/**
 * Funcion que contiene todas las operaciones de la Base de Datos
 * @author Luis Morales
 */
public class BaseDatos {
    
    //Implementa constantes de inicio de sesión
    public static final int DATO_INCORRECTO = 1;
    public static final int USUARIO_NO_ENCONTRADO = 2;
    public static final int ACCESO_CONCEDIDO = 3;
    
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    /**
     * Este constructor funciona para hacer conexion con la base de datos,
     * usando un URL y un Driver de conexion (8.0)
     **/
    
    public BaseDatos(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //PARTE 1: Operaciones de Insersion de Datos
    /**
     * Este metodo permite insertar un administrador a la base de datos por un comando SQL
     * @param admin : Un objeto de tipo admin_school que contiene los datos para agregar a la tabla correspondiente 
     **/
    public void insertarAdministrador(Admin_school admin){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO admin_school (id_user_admin, apellido_paterno_admin, apellido_materno_admin,"
                    + "nombre_admin, fecha_nacimiento_admin, telefono_admin, email_admin) VALUES (?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, admin.getId_user_admin());
            pstm.setString(2, admin.getApellido_paterno_admin());
            pstm.setString(3, admin.getApellido_materno_admin());
            pstm.setString(4, admin.getNombre_admin());
            pstm.setObject(5, admin.getFecha_nacimiento_admin());
            pstm.setString(6, admin.getTelefono_admin());
            pstm.setString(7, admin.getEmail_admin());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar una categoria a la base de datos por un comando SQL.
     * <br><br>
     * NOTA: Actualmente solo se puede seleccionar entre Children o Teens
     * @param category : Un objeto de tipo Category que contiene los datos para agregar a la tabla correspondiente 
     **/
    //
    public void insertarCategoria(Category category) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO category (description_category) VALUES (?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, category.getDescription_category());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un nuevo estatus de pago a la base de datos por un comando SQL
     * @param status : Un objeto de tipo Payment_status que contiene los datos para agregar a la tabla correspondiente 
     **/
    
    public void insertarEstatusDePago(Payment_status status) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO payment_status (description_status) VALUES (?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, status.getDescription_status());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un nuevo reporte de seguimiento de Pago a la base de datos por un comando SQL
     * @return id_pago
     * @param pay : Un objeto de tipo Payment que contiene los datos para agregar a la tabla correspondiente 
     **/
    public int insertarSeguimientoDePago(Payment pay) {
        int id_pay = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO payment(register_payment, pay_1, pay_2, pay_3, pay_4"
                    + ",pay_5, pay_6, pay_7, payment_status) VALUES (?,?,?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, (pay.isRegister_payment())? 1:0);
            pstm.setInt(2, pay.isPay_1()? 1:0);
            pstm.setInt(3, pay.isPay_2()? 1:0);
            pstm.setInt(4, pay.isPay_3()? 1:0);
            pstm.setInt(5, pay.isPay_4()? 1:0);
            pstm.setInt(6, pay.isPay_5()? 1:0);
            pstm.setInt(7, pay.isPay_6()? 1:0);
            pstm.setInt(8, pay.isPay_7()? 1:0);
            pstm.setInt(9, pay.getPayment_status());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                id_pay = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return id_pay;
    }
    
    /**
     * Este metodo permite insertar un estudiante a la base de datos por un comando SQL
     * @param student : Un objeto de tipo student que contiene los datos para agregar a la tabla correspondiente 
     **/
    public void insertarEstudiante(Students student) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO students (id_teacher_student, id_report_student, id_payment_student,"
                    + "id_user_student, apellido_paterno_student, apellido_materno_student, nombre_student, telefono1_student, "
                    + "telefono2_student, fecha_nacimiento_student, email_student, sale_solo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(sql);
            if(student.getId_teacher_student() == 0){
                pstm.setNull(1, java.sql.Types.INTEGER);
            }
            else{
                pstm.setInt(1, student.getId_teacher_student());
            }
            
            pstm.setInt(2, student.getId_report_student());
            pstm.setInt(3, student.getId_payment_student());
            pstm.setInt(4, student.getId_user_student());
            pstm.setString(5, student.getApellido_paterno_student());
            pstm.setString(6, student.getApellido_materno_student());
            pstm.setString(7, student.getNombre_student());
            pstm.setString(8, student.getTelefono1_student());
            pstm.setString(9, student.getTelefono2_student());
            pstm.setObject(10, student.getFecha_nacimiento_student());
            pstm.setString(11, student.getEmail_student());
            pstm.setInt(12, (student.isSale_solo())?1:0);
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un grupo a la base de datos por un comando SQL
     * @param group : Un objeto de tipo Grupos que contiene los datos para agregar a la tabla correspondiente 
     **/
    public void insertarGrupos(Grupos group) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO grupos (id_grade, level_group, id_category_group, classroom_group) VALUES (?,?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, group.getId_grade());
            pstm.setInt(2, group.getLevel_group());
            pstm.setInt(3, group.getId_category_group());
            pstm.setString(4, group.getClassroom_group());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un nivel a la base de datos por un comando SQL.
     * <br><br>
     * NOTA: Actualmente solo se puede seleccionar entre Basico, Intermedio o Avanzado
     * @param grade : Un objeto de tipo Grade o Nivel que contiene los datos para agregar a la tabla correspondiente 
     **/
    
    public void insertarNivel(Grade grade) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO grade (description_grade) VALUES (?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, grade.getDescription_grade());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Simbologia de Pagos
     * Este metodo permite insertar un mes como calendario y su costo a la base de datos por un comando SQL
     * @param symbol : Un objeto de tipo Pay_simbology que contiene los datos para agregar a la tabla correspondiente 
     **/

    public void insertarMes(Pay_simbology symbol) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO pay_simbology (month, description_pay, cost_pay, period_pay, deadline_pay) VALUES (?,?,?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, symbol.getMonth());
            pstm.setString(2, symbol.getDescription_pay());
            pstm.setDouble(3, symbol.getCost_pay());
            pstm.setString(4, symbol.getPeriod_pay());
            pstm.setObject(5, symbol.getDeadline_pay());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un nuevo reporte de calificaciones a la base de datos por un comando SQL
     * @return id_report
     * @param report : Un objeto de tipo report que contiene los datos para agregar a la tabla correspondiente 
     **/
    
    public int insertarReporteCalificaciones(Report report) {
        int id_reporte = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO report (first_partial_report, second_partial_report, avg_report) VALUES (?,?,?)";
            pstm = con.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
            pstm.setDouble(1, report.getFirst_partial_report());
            pstm.setDouble(2, report.getSecond_partial_report());
            pstm.setDouble(3, report.getAvg_report());
            pstm.executeUpdate();
            rs = pstm.getGeneratedKeys();
            if(rs.next()){
                id_reporte = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return id_reporte;
    }
    
    /**
     * Este metodo permite insertar un profesor a la base de datos por un comando SQL
     * @param teacher : Un objeto de tipo teacher que contiene los datos para agregar a la tabla correspondiente 
     **/    
    public void insertarTeacher(Teachers teacher) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO teachers (id_user_teacher, apellido_paterno_teacher, apellido_materno_teacher,"
                    + "nombre_teacher, telefono_teacher, email_teacher, fecha_nacimiento_teacher, status_teacher, "
                    + "id_group_teacher) VALUES (?,?,?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, teacher.getId_user_teacher());
            pstm.setString(2, teacher.getApellido_paterno_teacher());
            pstm.setString(3, teacher.getApellido_materno_teacher());
            pstm.setString(4, teacher.getNombre_teacher());
            pstm.setString(5, teacher.getTelefono_teacher());
            pstm.setString(6, teacher.getEmail_teacher());
            pstm.setObject(7, teacher.getFecha_nacimiento_teacher());
            pstm.setString(8, teacher.getStatus_teacher());
            if (teacher.getId_group_teacher() != 0)
                pstm.setInt(9, teacher.getId_group_teacher());
            else
                //En caso de que no tenga grupo, marca null en la BD
                pstm.setNull(9, java.sql.Types.INTEGER);
            
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite insertar un usuario a la base de datos por un comando SQL
     * @param user : Un objeto de tipo usuario que contiene los datos para agregar a la tabla correspondiente 
     **/
    public void insertarUsuario(Users user) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "INSERT INTO users (nom_user, password, rango) VALUES (?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, user.getNom_user());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getRango());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    //Parte 2: Operaciones de Actualización de Datos
    /**
     * Este metodo permite hacer una consulta SQL para actualizar los datos de un usuario especifico 
     * @param user: Un objeto de tipo User con todos sus datos 
     **/
    public void actualizarUsuario(Users user){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE users SET nom_user=?, password = ?, rango = ? WHERE id_user = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, user.getNom_user());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getRango());
            pstm.setInt(4, user.getId_user());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para actualizar los datos de un usuario especifico 
     * @param teacher: Un objeto de tipo Teacher con todos sus datos 
     **/
    public void actualizarTeacher(Teachers teacher){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE teachers SET apellido_paterno_teacher = ?, apellido_materno_teacher = ?, "
                    + "nombre_teacher = ?, telefono_teacher = ?, email_teacher = ?, fecha_nacimiento_teacher = ?,"
                    + "status_teacher = ?, id_group_teacher = ?, classroom_teacher = ? WHERE id_user_teacher = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, teacher.getApellido_paterno_teacher());
            pstm.setString(2, teacher.getApellido_materno_teacher());
            pstm.setString(3, teacher.getNombre_teacher());
            pstm.setString(4, teacher.getTelefono_teacher());
            pstm.setString(5, teacher.getEmail_teacher());
            pstm.setObject(6, teacher.getFecha_nacimiento_teacher());
            pstm.setString(7, teacher.getStatus_teacher());
            
            if (teacher.getId_group_teacher() != 0)
                pstm.setInt(8, teacher.getId_group_teacher());
            else
                //En caso de que no tenga grupo, marca null en la BD
                pstm.setNull(8, java.sql.Types.INTEGER);

            pstm.setString(9, teacher.getClassroom_teacher());
            pstm.setInt(10, teacher.getId_user_teacher());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL para actualizar los datos de un administrador especifico 
     * @param admin: Un objeto de tipo Admin_school con todos sus datos 
     **/
     public void actualizarAdministrador(Admin_school admin){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE admin_school SET apellido_paterno_admin = ?, apellido_materno_admin = ?, "
                    + "nombre_admin =?, fecha_nacimiento_admin = ?, telefono_admin = ?, email_admin = ? WHERE id_user_admin = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, admin.getApellido_paterno_admin());
            pstm.setString(2, admin.getApellido_materno_admin());
            pstm.setString(3, admin.getNombre_admin());
            pstm.setObject(4, admin.getFecha_nacimiento_admin());
            pstm.setString(5, admin.getTelefono_admin());
            pstm.setString(6, admin.getEmail_admin());
            pstm.setInt(7, admin.getId_user_admin());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
     
    /**
     * Este metodo permite hacer una consulta SQL para actualizar los datos de un estudiante especifico 
     * @param student: Un objeto de tipo Student con todos sus datos 
     **/ 
    public void actualizarEstudiante(Students student){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "UPDATE students SET "
                    + "apellido_paterno_student = ?, apellido_materno_student = ?, nombre_student = ?, telefono1_student = ?, "
                    + "telefono2_student = ?, fecha_nacimiento_student = ?, email_student= ?, sale_solo = ?,"
                    + "id_teacher_student = ? WHERE id_user_student = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, student.getApellido_paterno_student());
            pstm.setString(2, student.getApellido_materno_student());
            pstm.setString(3, student.getNombre_student());
            pstm.setString(4, student.getTelefono1_student());
            pstm.setString(5, student.getTelefono2_student());
            pstm.setObject(6, student.getFecha_nacimiento_student());
            pstm.setString(7, student.getEmail_student());
            pstm.setBoolean(8, student.isSale_solo());
            if (student.getId_teacher_student() != 0)
                pstm.setInt(9, student.getId_teacher_student());
            else
                //En caso de que no tenga grupo, marca null en la BD
                pstm.setNull(9, java.sql.Types.INTEGER);
            pstm.setInt(10, student.getId_user_student());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para actualizar el grupo del alumno
     * @param student: Un objeto de tipo Student con todos sus datos. 
     **/ 
    public void cambiarEstudianteDeGrupo(Students student){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "UPDATE students SET id_teacher_student = ? "
                    + "WHERE id_user_student = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1,student.getId_teacher_student());
            pstm.setInt(2, student.getId_user_student());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para actualizar las calificaciones de un alumno especifico 
     * @param report: Un objeto de tipo Report con todos sus datos 
     **/
    public void actualizarReporteCalificaciones(Report report) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE report SET first_partial_report = ?, second_partial_report = ?, avg_report = ? WHERE id_report = ?";
            pstm = con.prepareStatement(sql);
            pstm.setDouble(1, report.getFirst_partial_report());
            pstm.setDouble(2, report.getSecond_partial_report());
            pstm.setDouble(3, report.getAvg_report());
            pstm.setInt(4, report.getId_report());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL para actualizar la descripcion de un estatus de pago especifico 
     * @param status: Un objeto de tipo Payment_status con todos sus datos 
     **/
    public void actualizarEstatusDePago(Payment_status status) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE payment_status SET description_status = ? WHERE id_ststus = (?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, status.getDescription_status());
            pstm.setInt(2, status.getId_status());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL para actualizar el registro de pago de un alumno especifico. 
     * @param pay: Un objeto de tipo Payment con todos sus datos 
     **/
    public void actualizarSeguimientoDePago(Payment pay) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE payment SET register_payment = ?, pay_1 = ?, pay_2 = ?, pay_3 = ?, pay_4 = ?"
                    + ",pay_5 = ?, pay_6 = ?, pay_7 = ?, payment_status = ? WHERE id_payment = ?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, (pay.isRegister_payment())? 1:0);
            pstm.setInt(2, pay.isPay_1()? 1:0);
            pstm.setInt(3, pay.isPay_2()? 1:0);
            pstm.setInt(4, pay.isPay_3()? 1:0);
            pstm.setInt(5, pay.isPay_4()? 1:0);
            pstm.setInt(6, pay.isPay_5()? 1:0);
            pstm.setInt(7, pay.isPay_6()? 1:0);
            pstm.setInt(8, pay.isPay_7()? 1:0);
            pstm.setInt(9, pay.getPayment_status());
            pstm.setInt(10, pay.getId_payment());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL para actualizar la descripcion e informacion de un mes especifico
     * @param symbol: Un objeto de tipo Pay_simbology con todos sus datos 
     **/
    //Simbologia de Pagos
    public void actualizarCalendario(Pay_simbology symbol) {
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE pay_simbology SET month = ?, description_pay = ?, cost_pay = ?, period_pay = ?, "
                    + "deadline_pay = ? WHERE id_pay = ?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, symbol.getMonth());
            pstm.setString(2, symbol.getDescription_pay());
            pstm.setDouble(3, symbol.getCost_pay());
            pstm.setString(4, symbol.getPeriod_pay());
            if(symbol.getDeadline_pay() != null){
               pstm.setObject(5, symbol.getDeadline_pay()); 
            }
            else{
                pstm.setNull(5, java.sql.Types.DATE);
            }
            pstm.setInt(6, symbol.getId_pay());
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para actualizar la informacion de un grupo especifico
     * @param group: Un objeto de tipo Grupos con todos sus datos 
     **/
    public void actualizarGrupos(Grupos group){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE grupos SET id_grade =?, level_group = ?, id_category_group = ?, classroom_group = ? WHERE id_group = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, group.getId_grade());
            pstm.setInt(2, group.getLevel_group());
            pstm.setInt(3, group.getId_category_group());
            pstm.setString(4, group.getClassroom_group());
            pstm.setInt(5, group.getId_group());
            pstm.executeUpdate();
  
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL para actualizar la descripcion de un nivel especifico 
     * @param grade: Un objeto de tipo Grade con todos sus datos 
     **/
     public void actualizarNivel(Grade grade){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE grade SET description_grade = ? WHERE id_grade = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, grade.getDescription_grade());
            pstm.setInt(2, grade.getId_grade());
            rs = pstm.executeQuery();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
     
    /**
     * Este metodo permite hacer una consulta SQL para actualizar la descripcion de una categoria especifica
     * @param category: Un objeto de tipo Category con todos sus datos 
     **/ 
    public void actualizarCategorias(Category category){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "UPDATE category SET description_category = ? WHERE id_category = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, category.getDescription_category());
            pstm.setInt(2, category.getId_category());
            rs = pstm.executeQuery();

            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    
    //PARTE 3: Visualizacion de Tablas
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Usuarios
     * @return ArrayList: Lista de usuarios de la Base de datos
     **/
    public ArrayList<Users> obtenerUsuarios(){
        ArrayList<Users> listaUsuarios = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM users;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_user = rs.getInt("id_user");
                String nom_user = rs.getString("nom_user");
                String password = rs.getString("password");
                String rango = rs.getString("rango");
                
                Users user = new Users(id_user, nom_user, password, rango);
                listaUsuarios.add(user);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaUsuarios;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Teachers
     * @return ArrayList: Lista de profesores de la Base de datos
     **/
    public ArrayList<Teachers> obtenerTeachers(){
        ArrayList<Teachers> listaTeachers = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM teachers ORDER BY apellido_paterno_teacher ASC;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_teacher = rs.getInt("id_teacher");
                int id_user_teacher = rs.getInt("id_user_teacher");
                String apellido_paterno_teacher = rs.getString("apellido_paterno_teacher");
                String apellido_materno_teacher = rs.getString("apellido_materno_teacher");
                String nombre_teacher = rs.getString("nombre_teacher");
                String telefono_teacher = rs.getString("telefono_teacher");
                String email_teacher = rs.getString("email_teacher");
                Object fecha_nacimiento_teacher = rs.getObject("fecha_nacimiento_teacher");
                String status_teacher = rs.getString("status_teacher");
                int id_group_teacher = rs.getInt("id_group_teacher");
                String classroom_teacher = rs.getString("classroom_teacher");
                Teachers teacher = new Teachers(id_teacher, id_user_teacher, apellido_paterno_teacher, apellido_materno_teacher,
                                                nombre_teacher, telefono_teacher, email_teacher, fecha_nacimiento_teacher, 
                                                status_teacher, id_group_teacher, classroom_teacher);
                listaTeachers.add(teacher);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaTeachers;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Students
     * @return ArrayList: Lista de estudiantes de la Base de datos
     **/
    public ArrayList<Students> obtenerEstudiantes(){
        ArrayList<Students> listaStudents = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM STUDENTS ORDER BY apellido_paterno_student;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_student = rs.getInt("id_student");
                int id_teacher_student = rs.getInt("id_teacher_student");
                int id_report_student = rs.getInt("id_report_student");
                int id_payment_student = rs.getInt("id_payment_student");
                int id_user_student = rs.getInt("id_user_student");
                String apellido_paterno_student = rs.getString("apellido_paterno_student");
                String apellido_materno_student = rs.getString("apellido_materno_student");
                String nombre_student = rs.getString("nombre_student");
                String telefono1_student = rs.getString("telefono1_student");
                String telefono2_student = rs.getString("telefono2_student");
                Object fecha_nacimiento_student = rs.getObject("fecha_nacimiento_student");
                String email_student = rs.getString("email_student");
                boolean sale_solo = rs.getBoolean("sale_solo");
   
                
                Students student = new Students(id_student, id_teacher_student, id_report_student, id_payment_student, 
                                                id_user_student, apellido_paterno_student, apellido_materno_student,
                                                nombre_student, telefono1_student, telefono2_student, fecha_nacimiento_student,
                                                email_student, sale_solo);
                listaStudents.add(student);
                
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaStudents;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Admin_school
     * @return ArrayList: Lista de Administradores de la Base de datos
     **/
    public ArrayList<Admin_school> obtenerAdministrador(){
        ArrayList<Admin_school> listaAdministrador = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM admin_school ORDER BY apellido_paterno_admin;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_admin = rs.getInt("id_admin");
                int id_user_admin = rs.getInt("id_user_admin");
                String apellido_paterno_admin = rs.getString("apellido_paterno_admin");
                String apellido_materno_admin = rs.getString("apellido_materno_admin");
                String nombre_admin = rs.getString("nombre_admin");
                Object fecha_nacimiento_admin = rs.getObject("fecha_nacimiento_admin");
                String telefono_admin = rs.getString("telefono_admin");
                String email_admin = rs.getString("email_admin");
   
                
                Admin_school administrator = new Admin_school(id_admin, id_user_admin, apellido_paterno_admin, apellido_materno_admin,
                                                nombre_admin, fecha_nacimiento_admin,telefono_admin, email_admin);
                listaAdministrador.add(administrator);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAdministrador;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Report
     * @return ArrayList: Lista de las calificaciones de los alumnos registrados en la Base de datos
     **/
    public ArrayList<Report> obtenerCalificaciones(){
        ArrayList<Report> listaCalificaciones = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM report;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_report = rs.getInt("id_report");
                double first_partial_report = rs.getDouble("first_partial_report");
                double second_partial_report = rs.getDouble("second_partial_report");
                double avg_report = rs.getDouble("avg_report");
                
                Report reporte = new Report(id_report, first_partial_report, second_partial_report, avg_report);
                listaCalificaciones.add(reporte);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaCalificaciones;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Payment_Status
     * @return ArrayList: Lista de Descripcion de Estatus de pago de la Base de datos
     **/
    
    public ArrayList<Payment_status> obtenerEstatus(){
        ArrayList<Payment_status> listaEstatus = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM payment_status;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_status = rs.getInt("id_status");
                String description_status = rs.getString("description_status");
                
                Payment_status status = new Payment_status(id_status, description_status);
                listaEstatus.add(status);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaEstatus;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Payment
     * @return ArrayList: Lista de seguimiento de Pago de todos los alumnos de la Base de datos
     **/
    public ArrayList<Payment> obtenerSeguimiento(){
        ArrayList<Payment> listaSeguimiento = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM payment;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_payment = rs.getInt("id_status");
                boolean register_payment = rs.getBoolean("register_payment");
                boolean pay_1 = rs.getBoolean("pay_1");
                boolean pay_2 = rs.getBoolean("pay_2");
                boolean pay_3 = rs.getBoolean("pay_3");
                boolean pay_4 = rs.getBoolean("pay_4");
                boolean pay_5 = rs.getBoolean("pay_5");
                boolean pay_6 = rs.getBoolean("pay_6");
                boolean pay_7 = rs.getBoolean("pay_7");
                int payment_status = rs.getInt("payment_status");
                
                Payment pago = new Payment(id_payment, register_payment, pay_1, pay_2, pay_3, pay_4, pay_5, pay_6, pay_7, payment_status);
                listaSeguimiento.add(pago);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaSeguimiento;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Pay_simbology
     * @return ArrayList: Calendario con su simbologia de pago registrado en la Base de datos
     **/
    public ArrayList<Pay_simbology> obtenerCalendario(){
        ArrayList<Pay_simbology> calendario = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM pay_simbology;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_pay = rs.getInt("id_pay");
                String month = rs.getString("month");
                String description_pay = rs.getString("description_pay");
                double cost_pay = rs.getDouble("cost_pay");
                String period_pay = rs.getString("period_pay");
                Object deadline_pay = rs.getObject("deadline_pay");
                Pay_simbology mes = new Pay_simbology(id_pay, month, description_pay, cost_pay, period_pay, deadline_pay);
                calendario.add(mes);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return calendario;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Grupos 
     * @return ArrayList: Lista de grupos del Taller de Ingles de la Base de datos
     **/
    
    public ArrayList<Grupos> obtenerGrupos(){
        ArrayList<Grupos> listaGrupos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM grupos;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_group = rs.getInt("id_group");
                int id_grade = rs.getInt("id_grade");
                int level_group = rs.getInt("level_group");
                int id_category_group = rs.getInt("id_category_group");
                String classroom_group = rs.getString("classroom_group");
                
                Grupos grupo = new Grupos(id_group, id_grade, level_group, id_category_group, classroom_group);
                listaGrupos.add(grupo);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaGrupos;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Grade
     * @return ArrayList: Lista de niveles de Ingles de la Base de datos
     **/
    
    public ArrayList<Grade> obtenerNivel(){
        ArrayList<Grade> listaNiveles = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM grade;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_grade = rs.getInt("id_grade");
                String description_grade = rs.getString("description_grade");
                
                Grade nivel = new Grade(id_grade, description_grade);
                listaNiveles.add(nivel);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaNiveles;
    }
    
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de la tabla Category
     * @return ArrayList: Lista de Categorias de Ingles de la Base de datos
     **/
    
    public ArrayList<Category> obtenerCategorias(){
        ArrayList<Category> listaCategorias = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM category;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_category = rs.getInt("id_category");
                String description_category = rs.getString("description_category");
                
                Category cat = new Category(id_category, description_category);
                listaCategorias.add(cat);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaCategorias;
    }
    
    //PARTE 4: Visualizacion de Tablas (Registros Unicos)
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de un usuario especifico 
     * @param usuario: Un nombre de usuario para comparar en la base de datos 
     * @return ArrayList: Datos de un usuario especifico en la base de datos
     **/
    
    public ArrayList<Users> obtenerUsuario(String usuario){
        ArrayList<Users> listaUsuario = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM users WHERE nom_user = ? or id_user = ?;";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, usuario);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_user = rs.getInt("id_user");
                String nom_user = rs.getString("nom_user");
                String password = rs.getString("password");
                String rango = rs.getString("rango");
                
                Users user = new Users(id_user, nom_user, password, rango);
                listaUsuario.add(user);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaUsuario;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de un profesor especifico 
     * @param id_user: Id de usuario del profesor para comparar en la base de datos
     * @return ArrayList: Datos de un profesor especifico en la base de datos
     **/
    
    public ArrayList<Teachers> obtenerTeacher(int id_user){
        ArrayList<Teachers> listaTeachers = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM TEACHERS WHERE id_user_teacher = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_user);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_teacher = rs.getInt("id_teacher");
                int id_user_teacher = rs.getInt("id_user_teacher");
                String apellido_paterno_teacher = rs.getString("apellido_paterno_teacher");
                String apellido_materno_teacher = rs.getString("apellido_materno_teacher");
                String nombre_teacher = rs.getString("nombre_teacher");
                String telefono_teacher = rs.getString("telefono_teacher");
                String email_teacher = rs.getString("email_teacher");
                Object fecha_nacimiento_teacher = rs.getObject("fecha_nacimiento_teacher");
                String status_teacher = rs.getString("status_teacher");
                int id_group_teacher = rs.getInt("id_group_teacher");
                String classroom_teacher = rs.getString("classroom_teacher");
                Teachers teacher = new Teachers(id_teacher, id_user_teacher, apellido_paterno_teacher, apellido_materno_teacher,
                                                nombre_teacher, telefono_teacher, email_teacher, fecha_nacimiento_teacher, 
                                                status_teacher, id_group_teacher, classroom_teacher);
                listaTeachers.add(teacher);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaTeachers;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de un administrador especifico 
     * @param id_user: Id de usuario del administrador para comparar en la base de datos
     * @return ArrayList: Datos de un administrador especifico en la base de datos
     **/
     public ArrayList<Admin_school> obtenerAdministrador(int id_user){
        ArrayList<Admin_school> listaAdministrador = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM admin_school WHERE id_user_admin = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_user);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_admin = rs.getInt("id_admin");
                int id_user_admin = rs.getInt("id_user_admin");
                String apellido_paterno_admin = rs.getString("apellido_paterno_admin");
                String apellido_materno_admin = rs.getString("apellido_materno_admin");
                String nombre_admin = rs.getString("nombre_admin");
                Object fecha_nacimiento_admin = rs.getObject("fecha_nacimiento_admin");
                String telefono_admin = rs.getString("telefono_admin");
                String email_admin = rs.getString("email_admin");
   
                
                Admin_school administrator = new Admin_school(id_admin, id_user_admin, apellido_paterno_admin, apellido_materno_admin,
                                                nombre_admin, fecha_nacimiento_admin,telefono_admin, email_admin);
                listaAdministrador.add(administrator);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAdministrador;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar los datos de un alumno especifico 
     * @param id_user: Id de usuario del alumno para comparar en la base de datos
     * @return ArrayList: Datos de un  alumno especifico en la base de datos
     **/
     
    public ArrayList<Students> obtenerEstudiante(int id_user){
        ArrayList<Students> listaStudents = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT * FROM STUDENTS WHERE id_user_student = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_user);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_student = rs.getInt("id_student");
                int id_teacher_student = rs.getInt("id_teacher_student");
                int id_report_student = rs.getInt("id_report_student");
                int id_payment_student = rs.getInt("id_payment_student");
                int id_user_student = rs.getInt("id_user_student");
                String apellido_paterno_student = rs.getString("apellido_paterno_student");
                String apellido_materno_student = rs.getString("apellido_materno_student");
                String nombre_student = rs.getString("nombre_student");
                String telefono1_student = rs.getString("telefono1_student");
                String telefono2_student = rs.getString("telefono2_student");
                Object fecha_nacimiento_student = rs.getObject("fecha_nacimiento_student");
                String email_student = rs.getString("email_student");
                boolean sale_solo = rs.getBoolean("sale_solo");
   
                
                Students student = new Students(id_student, id_teacher_student, id_report_student, id_payment_student, 
                                                id_user_student, apellido_paterno_student, apellido_materno_student,
                                                nombre_student, telefono1_student, telefono2_student, fecha_nacimiento_student,
                                                email_student, sale_solo);
                listaStudents.add(student);
                
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaStudents;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar las calificaciones de un alumno especifico
     * @param id: Id de la lista de calificaciones a buscar
     * @return ArrayList: Lista de calificaciones de un alumno especifico
     **/
    public ArrayList<Report> obtenerCalificaciones(int id){
        ArrayList<Report> listaCalificaciones = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM report WHERE id_report = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_report = rs.getInt("id_report");
                double first_partial_report = rs.getDouble("first_partial_report");
                double second_partial_report = rs.getDouble("second_partial_report");
                double avg_report = rs.getDouble("avg_report");
                
                Report reporte = new Report(id_report, first_partial_report, second_partial_report, avg_report);
                listaCalificaciones.add(reporte);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaCalificaciones;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar un estatus de pago especifico
     * @param id: Id de la descripcion de estatus en la base de datos
     * @return ArrayList: Estatus e identificador en la base de datos
     **/
    
    public String obtenerEstatus(int id){
        ArrayList<Payment_status> listaEstatus = new ArrayList<>();
        String estatusEncontrado = "";
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM payment_status WHERE id_status = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_status = rs.getInt("id_status");
                String description_status = rs.getString("description_status");
                
                Payment_status status = new Payment_status(id_status, description_status);
                listaEstatus.add(status);
                estatusEncontrado = description_status;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return estatusEncontrado;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar el seguimiento de pago de un alumno especifico
     * @param id: Id de la lista de seguimiento buscado en la base de datos
     * @return ArrayList: Lista de seguimiento de un alumno especifico
     **/
    
    public ArrayList<Payment> obtenerSeguimiento(int id){
        ArrayList<Payment> listaSeguimiento = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM payment WHERE id_payment = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_payment = rs.getInt("id_payment");
                boolean register_payment = rs.getBoolean("register_payment");
                boolean pay_1 = rs.getBoolean("pay_1");
                boolean pay_2 = rs.getBoolean("pay_2");
                boolean pay_3 = rs.getBoolean("pay_3");
                boolean pay_4 = rs.getBoolean("pay_4");
                boolean pay_5 = rs.getBoolean("pay_5");
                boolean pay_6 = rs.getBoolean("pay_6");
                boolean pay_7 = rs.getBoolean("pay_7");
                int payment_status = rs.getInt("payment_status");
                
                Payment pago = new Payment(id_payment, register_payment, pay_1, pay_2, pay_3, pay_4, pay_5, pay_6, pay_7, payment_status);
                listaSeguimiento.add(pago);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaSeguimiento;
    }
    
    public String obtenerPeriodo(String mes){
        String periodoEncontrado = "";
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT period_pay FROM pay_simbology WHERE month = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, mes);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                periodoEncontrado = rs.getString("period_pay");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return periodoEncontrado;
    }
    /**
     * Este metodo permite hacer una consulta SQL para regresar la informacion de un mes especifico
     * @param periodo: Periodo Escolar buscado en la base de datos
     * @return ArrayList: Informacion del mes solicitado
     **/
    
    public ArrayList<Pay_simbology> obtenerCalendario(String periodo){
        ArrayList<Pay_simbology> calendario = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM pay_simbology WHERE period_pay = ? OR period_pay = 'Cualquiera';";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, periodo);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_pay = rs.getInt("id_pay");
                String month = rs.getString("month");
                String description_pay = rs.getString("description_pay");
                double cost_pay = rs.getDouble("cost_pay");
                String period_pay = rs.getString("period_pay");
                Object deadline_pay = rs.getObject("deadline_pay");
                Pay_simbology mes = new Pay_simbology(id_pay, month, description_pay, cost_pay, period_pay, deadline_pay);
                calendario.add(mes);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return calendario;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar la informacion de un mes especifico
     * @param id_month: Periodo Escolar buscado en la base de datos
     * @return ArrayList: Informacion del mes solicitado
     **/
    
    public ArrayList<Pay_simbology> obtenerDatosDeMes(int id_month){
        ArrayList<Pay_simbology> calendario = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM pay_simbology WHERE id_pay = ? ;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_month);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_pay = rs.getInt("id_pay");
                String month = rs.getString("month");
                String description_pay = rs.getString("description_pay");
                double cost_pay = rs.getDouble("cost_pay");
                String period_pay = rs.getString("period_pay");
                Object deadline_pay = rs.getObject("deadline_pay");
                Pay_simbology mes = new Pay_simbology(id_pay, month, description_pay, cost_pay, period_pay, deadline_pay);
                calendario.add(mes);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return calendario;
    }
    /**
     * Este metodo permite hacer una consulta SQL para regresar la informacion de un grupo especifico
     * @param id: Id del grupo  buscado en la base de datos
     * @return ArrayList: Informacion del grupo solicitado
     **/
    public ArrayList<Grupos> obtenerGrupos(int id){
        ArrayList<Grupos> listaGrupos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM GRUPOS WHERE id_group = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_group = rs.getInt("id_group");
                int id_grade = rs.getInt("id_grade");
                int level_group = rs.getInt("level_group");
                int id_category_group = rs.getInt("id_category_group");
                String classroom_group = rs.getString("classroom_group");
                
                Grupos grupo = new Grupos(id_group, id_grade, level_group, id_category_group, classroom_group);
                listaGrupos.add(grupo);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaGrupos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL para regresar la informacion de un nivel especifico. 
     * <br>
     * NOTA: Actualmente solo se puede seleccionar entre Basico, Intermedio o Avanzado
     * @param id: Id del nivel buscado en la base de datos
     * @return ArrayList: Informacion del nivel solicitado
     **/
    public String obtenerNivel(int id){
        ArrayList<Grade> listaNiveles = new ArrayList<>();
        String nivelObtenido = "";
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM grade WHERE id_grade = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_grade = rs.getInt("id_grade");
                String description_grade = rs.getString("description_grade");
                
                Grade nivel = new Grade(id_grade, description_grade);
                listaNiveles.add(nivel);
                nivelObtenido = description_grade;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return nivelObtenido;
    }
     
    /**
     * Este metodo permite hacer una consulta SQL para regresar la informacion de una categoria especifica. 
     * <br>
     * NOTA: Actualmente solo se puede seleccionar entre Children o Teens
     * @param id: Id de la categroia en la base de datos
     * @return ArrayList: Informacion de la categoria solicitada
     **/
    public String obtenerCategorias(int id){
        ArrayList<Category> listaCategorias = new ArrayList<>();
        String categoriaObtenida = "";
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT * FROM category WHERE id_category = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_category = rs.getInt("id_category");
                String description_category = rs.getString("description_category");
                
                Category cat = new Category(id_category, description_category);
                listaCategorias.add(cat);
                categoriaObtenida = description_category;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return categoriaObtenida;
    }
    
    //PARTE 5: Visualizacion de Consultas
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en los administradores y visualizar su informacion en la pagina web. 
     * <br>
     * 
     * @return ArrayList: Informacion del administrador localizada en diferentes tablas.
     **/
    
    public ArrayList<ConsultasAdmin> obtenerDatosAdministradores(){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultasAdmin> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT admin_school.id_admin, admin_school.apellido_paterno_admin, admin_school.apellido_materno_admin, \n" +
                        "admin_school.nombre_admin, users.rango, users.id_user, users.nom_user, admin_school.telefono_admin, \n" + 
                        "admin_school.email_admin, admin_school.fecha_nacimiento_admin FROM admin_school \n" +
                        "INNER JOIN users ON users.id_user = admin_school.id_user_admin";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Administrador
                int id_admin = rs.getInt("id_admin");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_admin");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_admin");
                //3. Nombre
                String nombre = rs.getString("nombre_admin");
                //4. Rango del Usuario
                String rango = rs.getString("rango");
                //5. Id de Usuario
                int id_user = rs.getInt("id_user");
                //6. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //7. Telefono
                String telefono_admin = rs.getString("telefono_admin");
                //8. Email
                String email_admin = rs.getString("email_admin");              
                //9. Fecha de nacimiento
                Object fecha_nacimiento = rs.getObject("fecha_nacimiento_admin");
                
                ConsultasAdmin consulta = new ConsultasAdmin(id_admin, apellido_paterno, 
                        apellido_materno, nombre, rango, id_user, nom_user, telefono_admin, email_admin, fecha_nacimiento);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    public int actualizarListas(){
        int ultimoRegistro = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT MAX(id_payment) AS ultimo_registro FROM payment;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            if(rs.next()){
                ultimoRegistro = rs.getInt("ultimo_registro");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return ultimoRegistro;
    }
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en los alumnos y visualizar su informacion en la pagina web. 
     * <br>
     * 
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaAlumnos> obtenerDatosAlumnos(){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaAlumnos> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, \n" +
                            "students.nombre_student, users.nom_user, students.telefono1_student, students.telefono2_student, \n" +
                            "students.id_teacher_student, users.rango,\n" +
                            "students.id_user_student, students.id_payment_student, \n" +
                            "students.id_report_student, students.email_student, students.sale_solo, students.fecha_nacimiento_student \n"
                            + " FROM STUDENTS \n" +
                            "INNER JOIN users ON users.id_user = students.id_user_student";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Alumno
                int id_student = rs.getInt("id_student");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_student");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_student");
                //3. Nombre
                String nombre = rs.getString("nombre_student");
                //4. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //5. Numero de Telefono 1
                String tel1 = rs.getString("telefono1_student");
                //6. Numero de Telefono 2
                String tel2 = rs.getString("telefono2_student");
                //7. Id del Profesor
                int id_teacher = rs.getInt("id_teacher_student"); 
                //8. Rango del Usuario
                String rango = rs.getString("rango");
                //9. Id de Usuario
                int id_user = rs.getInt("id_user_student");
                //10. Id de lista de pagos
                int pago = rs.getInt("id_payment_student");
                //11. Id de lista de calificaciones
                int calificaciones = rs.getInt("id_report_student");
                //12. Correo electronico
                String email = rs.getString("email_student");
                //13. ¿Sale Solo?
                boolean saleSolo = rs.getBoolean("sale_solo");
                //14. Fecha de Nacimiento
                Object fecha = rs.getObject("fecha_nacimiento_student");  
                
                ConsultaAlumnos consulta = new ConsultaAlumnos(id_student, apellido_paterno, apellido_materno, nombre, 
                                            nom_user, tel1, tel2, id_teacher, rango, id_user, pago, calificaciones, email,
                                            saleSolo, fecha);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas de todos los grupos de la
     * base de datos y visualizar toda la informacion en la pagina web. 
     * <br>
     * 
     * @return ArrayList: Informacion del todos los grupos localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaGrupos> obtenerDatosGrupos(){
        ArrayList <ConsultaGrupos> datosGrupo = new ArrayList<>();
         try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT grupos.id_group, grupos.id_grade, grade.description_grade ," +
                            "grupos.level_group,  grupos.id_category_group, category.description_category FROM " +
                            "(" +
                            "	(grupos INNER JOIN grade ON grupos.id_grade = grade.id_grade) " +
                            "	INNER JOIN category ON grupos.id_category_group = category.id_category" +
                            ") ORDER BY grupos.id_group;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Grupo
                int idGrupo = rs.getInt(1);
                //1. Id del Grado
                int id_grade = rs.getInt(2);
                //2. Descripcion del Grado
                String grado = rs.getString(3);
                //3. Nivel del Grupo
                int nivel = rs.getInt(4);
                //4. Id de Categoria
                int idCategory = rs.getInt(5);
                //5. Descripcion de Categoria
                String categoria = rs.getString(6);
                
                ConsultaGrupos cons = new ConsultaGrupos(idGrupo, id_grade, grado, nivel, idCategory, categoria);
                datosGrupo.add(cons);
            }   
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosGrupo;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en todos los profesores 
     * y visualizar su informacion en la pagina web. 
     * <br>
     * 
     * @return ArrayList: Informacion del profesor localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaTeacher> obtenerDatosProfesores(){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaTeacher> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT teachers.id_teacher, teachers.apellido_paterno_teacher, teachers.apellido_materno_teacher,\n" +
                            "teachers.nombre_teacher, teachers.fecha_nacimiento_teacher, teachers.telefono_teacher, \n" +
                            "teachers.email_teacher, teachers.id_group_teacher, teachers.status_teacher, users.rango, users.id_user, users.nom_user, "
                            + "teachers.classroom_teacher FROM TEACHERS \n" +
                            "INNER JOIN users ON users.id_user = teachers.id_user_teacher ;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Profesor
                int id_teacher = rs.getInt("id_teacher");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_teacher");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_teacher");
                //3. Nombre
                String nombre = rs.getString("nombre_teacher");
                //4. Fecha de Nacimiento
                Object fecha = rs.getObject("fecha_nacimiento_teacher");
                //5. Numero de Telefono 
                String tel1 = rs.getString("telefono_teacher");
                //6. Correo Electronico
                String email = rs.getString("email_teacher");
                //7. Id del grupo
                int id_grupo = rs.getInt("id_group_teacher");
                //8. Status
                String status = rs.getString("status_teacher");
                //9. Rango del Usuario
                String rango = rs.getString("rango");
                //10. Id del Usuario
                int id_user = rs.getInt("id_user");
                //11. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //12. Salon de clases
                String classroom = rs.getString("classroom_teacher");
                ConsultaTeacher consulta = new ConsultaTeacher(id_teacher, apellido_paterno, apellido_materno, nombre,
                                                        fecha, tel1, email, id_grupo, status, rango, id_user,nom_user, classroom);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    //PARTE 6: Visualizacion de Consultas (Registros Unicos)
    
    /**
     * Este metodo permite el inicio de sesion a la pagina web por medio de una consulta SQL.
     * @param user : El nombre de Usuario para acceder al sistema
     * @param password : Contraseña del usuario para Ingresar al sistema
     * @param rol: Rol que tiene el usuario dentro de la base de datos. <br>(ESTUDIANTE, PROFESOR, ADMINISTRATIVO)
     * @return int Devuelve la accion que debe tomar la pagina web. 
     *          DATO_INCORRECTO = 1 <br>
     *          USUARIO_NO_ENCONTRADO = 2<br>
     *          ACCESO_CONCEDIDO = 3<br>
     **/
    public int inicioSesion(String user, String password, String rol){
        int pagina = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String SQL = "SELECT * FROM users WHERE rango = (?) and nom_user = (?) " ;
            pstm = con.prepareStatement(SQL);
            pstm.setString(1, rol);
            pstm.setString(2, user);
            rs = pstm.executeQuery();
            //String SQL2;
            
            if(rs.next()){
                String contraBD = rs.getString("password");
                if(password.equals(contraBD)){
                    //3. Menu de inicio de cierto rol
                    pagina = ACCESO_CONCEDIDO;
                    
                }else{
                    //1 = Un dato es incorrecto
                    pagina  = DATO_INCORRECTO;
                }
            }
            else{
                pagina = USUARIO_NO_ENCONTRADO;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            //pagina = USUARIO_NO_ENCONTRADO;
            
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }  
        }
        return pagina;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en un
     * administrador UNICO y visualizar su informacion en la pagina web. 
     * <br>
     * @param usuario: Nombre de usuario a buscar en la base de datos
     * @return ArrayList: Informacion del administrador localizada en diferentes tablas.
     **/
    
    public ArrayList<ConsultasAdmin> obtenerDatosAdministrador(String usuario){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultasAdmin> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT admin_school.id_admin, admin_school.apellido_paterno_admin, admin_school.apellido_materno_admin, \n" +
                        "admin_school.nombre_admin, users.rango, users.id_user, users.nom_user, admin_school.telefono_admin, \n" + 
                        "admin_school.email_admin, admin_school.fecha_nacimiento_admin FROM admin_school \n" +
                        "INNER JOIN users ON users.id_user = admin_school.id_user_admin \n" +
                        "WHERE nom_user = ? OR admin_school.id_admin = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, usuario);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Administrador
                int id_admin = rs.getInt("id_admin");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_admin");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_admin");
                //3. Nombre
                String nombre = rs.getString("nombre_admin");
                //4. Rango del Usuario
                String rango = rs.getString("rango");
                //5. Id de Usuario
                int id_user = rs.getInt("id_user");
                //6. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //7. Telefono
                String telefono_admin = rs.getString("telefono_admin");
                //8. Email
                String email_admin = rs.getString("email_admin");              
                //9. Fecha de nacimiento
                Object fecha_nacimiento = rs.getObject("fecha_nacimiento_admin");
                
                ConsultasAdmin consulta = new ConsultasAdmin(id_admin, apellido_paterno, 
                        apellido_materno, nombre, rango, id_user, nom_user, telefono_admin, email_admin, fecha_nacimiento);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    
    
    
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en 
     * un alumno UNICO y visualizar su informacion en la pagina web. 
     * <br>
     * @param usuario: Nombre de usuario a buscar en la base de datos
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaAlumnos> obtenerDatosAlumno(String usuario){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaAlumnos> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, \n" +
                            "students.nombre_student, users.nom_user, students.telefono1_student, students.telefono2_student, \n" +
                            "students.id_teacher_student, users.rango,\n" +
                            "students.id_user_student, students.id_payment_student, \n" +
                            "students.id_report_student, students.email_student, students.sale_solo, students.fecha_nacimiento_student \n"
                            + " FROM STUDENTS \n" +
                            "INNER JOIN users ON users.id_user = students.id_user_student \n" +
                            "WHERE nom_user = ? OR students.id_student = ? ;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, usuario);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Alumno
                int id_student = rs.getInt("id_student");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_student");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_student");
                //3. Nombre
                String nombre = rs.getString("nombre_student");
                //4. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //5. Numero de Telefono 1
                String tel1 = rs.getString("telefono1_student");
                //6. Numero de Telefono 2
                String tel2 = rs.getString("telefono2_student");
                //7. Id del Profesor
                int id_teacher = rs.getInt("id_teacher_student"); 
                //8. Rango del Usuario
                String rango = rs.getString("rango");
                //9. Id de Usuario
                int id_user = rs.getInt("id_user_student");
                //10. Id de lista de pagos
                int pago = rs.getInt("id_payment_student");
                //11. Id de lista de calificaciones
                int calificaciones = rs.getInt("id_report_student");
                //12. Correo electronico
                String email = rs.getString("email_student");
                //13. ¿Sale Solo?
                boolean saleSolo = rs.getBoolean("sale_solo");
                //14. Fecha de Nacimiento
                Object fecha = rs.getObject("fecha_nacimiento_student");  
                
                ConsultaAlumnos consulta = new ConsultaAlumnos(id_student, apellido_paterno, apellido_materno, nombre, 
                                            nom_user, tel1, tel2, id_teacher, rango, id_user, pago, calificaciones, email,
                                            saleSolo, fecha);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas de un grupo UNICO de la
     * base de datos y visualizar toda la informacion en la pagina web. 
     * <br>
     * @param id_grupo: Id del grupo a buscar en la base de datos
     * @return ArrayList: Informacion del todos los grupos localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaGrupos> obtenerDatosGrupo(int id_grupo){
        ArrayList <ConsultaGrupos> datosGrupo = new ArrayList<>();
         try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT grupos.id_group, grupos.id_grade, grade.description_grade ," +
                            "grupos.level_group,  grupos.id_category_group, category.description_category FROM " +
                            "(" +
                            "	(grupos INNER JOIN grade ON grupos.id_grade = grade.id_grade) " +
                            "	INNER JOIN category ON grupos.id_category_group = category.id_category" +
                            ") WHERE id_group = ?;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_grupo);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Grupo
                int idGrupo = rs.getInt(1);
                //1. Id del Grado
                int id_grade = rs.getInt(2);
                //2. Descripcion del Grado
                String grado = rs.getString(3);
                //3. Nivel del Grupo
                int nivel = rs.getInt(4);
                //4. Id de Categoria
                int idCategory = rs.getInt(5);
                //5. Descripcion de Categoria
                String categoria = rs.getString(6);
                ConsultaGrupos cons = new ConsultaGrupos(idGrupo, id_grade, grado, nivel, idCategory, categoria);
                datosGrupo.add(cons);
            }   
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosGrupo;
    }
    
    /**
     * Este metodo permite concatenar datos de una consulta SQL de varias tablas enfocada en un grupo. 
     * <br>
     * 
     * @param id_grupo: Id del grupo a obtener 
     * @return String: Concatenacion de grupo.
     **/
    public String concatenarInfoGrupo(int id_grupo){
        String datosGrupo = "";
        try{
            ArrayList<ConsultaGrupos> consulta = obtenerDatosGrupo(id_grupo);
            Iterator <ConsultaGrupos> iter = consulta.iterator();
            ConsultaGrupos per = null;
            if(iter.hasNext()){
                per = iter.next();
                String grade = per.getDescription_grade();
                int level = per.getLevel_group();
                String category = per.getDescription_category();
                datosGrupo = grade + " " + level + ": " + category;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosGrupo;
    }
    
    /**
     * Este metodo permite concatenar datos de una consulta SQL de varias tablas enfocada en un grupo. 
     * <br>
     * 
     * @param id_prof: Id del profesor responsable 
     * @return String: Concatenacion de grupo.
     **/
    public String concatenarDatosGrupo(int id_prof){
        String datosGrupo = "";
         try{
            if(id_prof == 0 ){
                datosGrupo = "Ningun grupo registrado"; 
            }
            else{
                int grupo = 0;
                
                ArrayList <ConsultaTeacher> consultaTeacher = obtenerDatosProfesores(String.valueOf(id_prof));
                Iterator <ConsultaTeacher> iterTeacher = consultaTeacher.iterator();
                ConsultaTeacher perTeacher = null;
                if (iterTeacher.hasNext()){
                    perTeacher = iterTeacher.next();
                    grupo = perTeacher.getId_group_teacher();
                    
                    ArrayList<ConsultaGrupos> consulta = obtenerDatosGrupo(grupo);
                    Iterator <ConsultaGrupos> iter = consulta.iterator();
                    ConsultaGrupos per = null;
                    if(iter.hasNext()){
                        per = iter.next();
                        String grade = per.getDescription_grade();
                        int level = per.getLevel_group();
                        String category = per.getDescription_category();
                        datosGrupo = grade + " " + level + ": " + category;
                    }
                }
                   
            }
             
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosGrupo;
    }

    /**
     * Este metodo permite concatenar datos de una consulta SQL de varias tablas enfocada en un profesor. 
     * <br>
     * 
     * @param id_prof: Id del profesor responsable 
     * @return String: Concatenacion de grupo.
     **/
    public String concatenarDatosProfesor(int id_prof){
        String datosProf = "";
         try{
            if(id_prof == 0 ){
                datosProf = "Ningun profesor registrado"; 
            }
            
            else{
                ArrayList<ConsultaTeacher> consulta = obtenerDatosProfesores(String.valueOf(id_prof));
                Iterator <ConsultaTeacher> iter = consulta.iterator();
                ConsultaTeacher per = null;
                if(iter.hasNext()){
                    per = iter.next();
                    String apellidoP = per.getApellido_paterno_teacher();
                    String apellidoM = per.getApellido_materno_teacher();
                    String nombre = per.getNombre_teacher();
                    datosProf = apellidoP + " " + apellidoM + " " + nombre;
                }   
            }
             
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosProf;
    }
    
    /**
     * Este metodo permite concatenar datos de una consulta SQL de varias tablas enfocada en un alumno. 
     * <br>
     * 
     * @param id_student: Id del alumno 
     * @return String: Concatenacion de grupo.
     **/
    public String concatenarDatosAlumno(int id_student){
        String datosAlumno = "";
         try{
            ArrayList<ConsultaAlumnos> consulta = obtenerDatosAlumno(String.valueOf(id_student));
            Iterator <ConsultaAlumnos> iter = consulta.iterator();
            ConsultaAlumnos per = null;
            if(iter.hasNext()){
                per = iter.next();
                String apellidoP = per.getApellido_paterno_student();
                String apellidoM = per.getApellido_materno_student();
                String nombre = per.getNombre_student();
                datosAlumno = apellidoP + " " + apellidoM + " " + nombre;
            }   
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosAlumno;
    }
    
    /**
     * Este metodo permite concatenar datos de una consulta SQL de varias tablas enfocada en un alumno. 
     * <br>
     * 
     * @param id_admin: Id del administrador
     * @return String: Concatenacion de grupo.
     **/
    public String concatenarDatosAdministrador(int id_admin){
        String datosAdmin = "";
         try{
            ArrayList<ConsultasAdmin> consulta = obtenerDatosAdministrador(String.valueOf(id_admin));
            Iterator <ConsultasAdmin> iter = consulta.iterator();
            ConsultasAdmin per = null;
            if(iter.hasNext()){
                per = iter.next();
                String apellidoP = per.getApellido_paterno_admin();
                String apellidoM = per.getApellido_materno_admin();
                String nombre = per.getNombre_admin();
                datosAdmin = apellidoP + " " + apellidoM + " " + nombre;
            }   
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return datosAdmin;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en un profesor UNICO 
     * y visualizar su informacion en la pagina web. 
     * <br>
     * @param usuario: Nombre de usuario a buscar en la base de datos
     * @return ArrayList: Informacion del profesor localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaTeacher> obtenerDatosProfesores(String usuario){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaTeacher> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT teachers.id_teacher, teachers.apellido_paterno_teacher, teachers.apellido_materno_teacher,\n" +
                            "teachers.nombre_teacher, teachers.fecha_nacimiento_teacher, teachers.telefono_teacher, \n" +
                            "teachers.email_teacher, teachers.id_group_teacher, teachers.status_teacher, users.rango, users.id_user, users.nom_user, "
                            + " teachers.classroom_teacher FROM TEACHERS \n" +
                            "INNER JOIN users ON users.id_user = teachers.id_user_teacher \n" +
                            "WHERE nom_user = (?) OR teachers.id_teacher = (?) ;";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, usuario);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Profesor
                int id_teacher = rs.getInt("id_teacher");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_teacher");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_teacher");
                //3. Nombre
                String nombre = rs.getString("nombre_teacher");
                //4. Fecha de Nacimiento
                Object fecha = rs.getObject("fecha_nacimiento_teacher");
                //5. Numero de Telefono 
                String tel1 = rs.getString("telefono_teacher");
                //6. Correo Electronico
                String email = rs.getString("email_teacher");
                //7. Id del grupo
                int id_grupo = rs.getInt("id_group_teacher");
                //8. Status
                String status = rs.getString("status_teacher");
                //9. Rango del Usuario
                String rango = rs.getString("rango");
                //10. Id del Usuario
                int id_user = rs.getInt("id_user");
                //11. Nombre de Usuario
                String nom_user = rs.getString("nom_user");
                //12. Salon de clases
                String classroom = rs.getString("classroom_teacher");
                ConsultaTeacher consulta = new ConsultaTeacher(id_teacher, apellido_paterno, apellido_materno, nombre,
                                                        fecha, tel1, email, id_grupo, status, rango, id_user,nom_user, classroom);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de conteo para saber la cantidad de alumnos registrados en la Base de datos. 
     * <br>
     * @return int: Cantidad de grupos en la base de datos. 
     **/
    public int conteoAlumnos(){
        int conteo = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT COUNT(*) FROM STUDENTS;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                conteo = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return conteo;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de conteo para saber la cantidad de profesores registrados en la Base de datos. 
     * <br>
     * @return int: Cantidad de grupos en la base de datos. 
     **/
    public int conteoProfesores(){
        int conteo = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT COUNT(*) FROM TEACHERS;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                conteo = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return conteo;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de conteo para saber la cantidad de alumnos registrados en un grupo especifico 
     * en la Base de datos. De lo contarrio, regresa cuantos no tienen un grupo asignado.  
     * <br>
     * @param  id_teacher: Id del profesor
     * @return int: Cantidad de grupos en la base de datos. 
     **/
    public int conteoAlumnos(int id_teacher){
        int conteo = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            if (id_teacher != 0){
                String sql = "SELECT COUNT(*) FROM STUDENTS WHERE id_teacher_student = ?;";
                pstm = con.prepareStatement(sql);
                pstm.setInt(1, id_teacher);
                rs = pstm.executeQuery();
            }
            else{
                String sql = "SELECT COUNT(*) FROM STUDENTS WHERE id_teacher_student IS NULL ;";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
            }
            while(rs.next()){
                conteo = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return conteo;
    }
    /**
     * Este metodo permite hacer una consulta SQL de conteo de meses en un periodo Especificico 
     * en la Base de datos. De lo contarrio, regresa cuantos no tienen un grupo asignado.  
     * <br>
     * @param  periodoActual: Id del profesor
     * @return int: Cantidad de grupos en la base de datos. 
     **/
    public int conteoMeses(String periodoActual){
        int conteo = 0;
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
                String sql = "SELECT COUNT(*) FROM PAY_SIMBOLOGY WHERE period_pay = ?;";
                pstm = con.prepareStatement(sql);
                pstm.setString(1, periodoActual);
                rs = pstm.executeQuery();
            while(rs.next()){
                conteo = rs.getInt(1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return conteo;
    }
    //Parte 7: Generacion de Documentos u otras consultas
    /**
     * Este metodo permite hacer una consulta SQL de la tabla de Alumnos.<br>
     * Tambien funciona para hacer el reporte correspondiente a la Bitacora de Asistencia
     * <br>
     * @param id_teacher: Id del profesor a buscar en la base de datos
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaBitacorasAlumnos> obtenerBitacorasDeAlumnos(int id_teacher){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaBitacorasAlumnos> listaAlumnos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT id_student, apellido_paterno_student, apellido_materno_student, nombre_student, sale_solo FROM students\n" +
                         "WHERE id_teacher_student = ? ;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_teacher);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Alumno
                int id_student = rs.getInt("id_student");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_student");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_student");
                //3. Nombre
                String nombre = rs.getString("nombre_student");
                //4. ¿Sale solo?
                boolean sale_solo = rs.getBoolean("sale_solo");
                ConsultaBitacorasAlumnos consulta = new ConsultaBitacorasAlumnos(id_student, apellido_paterno, apellido_materno, nombre, sale_solo);
                listaAlumnos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de la tabla de Alumnos.<br>
     * Tambien funciona para hacer el reporte correspondiente a la Bitacora de Asistencia
     * <br>
     * @param id_teacher: Id del profesor a buscar en la base de datos
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaPagos> obtenerListasSeguimientoDePago(int id_teacher){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaPagos> listaAlumnos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, students.nombre_student, students.telefono1_student,  "
                        + "payment.id_payment, payment.register_payment, payment.pay_1, payment.pay_2, payment.pay_3, payment.pay_4, "
                        + "payment.pay_5, payment.pay_6, payment.pay_7, payment.payment_status, payment_status.description_status FROM students \n"
                        + "INNER JOIN payment ON payment.id_payment = students.id_payment_student \n"
                        + "INNER JOIN payment_status ON payment.payment_status = payment_status.id_status \n" +
                          "WHERE id_teacher_student = ? ;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_teacher);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                int id_student = rs.getInt("id_student");
                String apellido_paterno_student = rs.getString("apellido_paterno_student");
                String apellido_materno_student = rs.getString("apellido_materno_student");
                String nombre_student = rs.getString("nombre_student");
                String telefono1_student = rs.getString("telefono1_student");
                int id_payment = rs.getInt("id_payment");
                boolean register_payment = rs.getBoolean("register_payment");
                boolean pay_1 = rs.getBoolean("pay_1");
                boolean pay_2 = rs.getBoolean("pay_2");
                boolean pay_3 = rs.getBoolean("pay_3");
                boolean pay_4 = rs.getBoolean("pay_4");
                boolean pay_5 = rs.getBoolean("pay_5");
                boolean pay_6 = rs.getBoolean("pay_6");
                boolean pay_7 = rs.getBoolean("pay_7");
                int payment_status = rs.getInt("payment_status");
                String description_status = rs.getString("description_status");
                ConsultaPagos consulta = new ConsultaPagos(id_student,apellido_paterno_student, apellido_materno_student, nombre_student, telefono1_student,
                                        id_payment, register_payment, pay_1, pay_2, pay_3, pay_4, pay_5, pay_6, pay_7, payment_status, description_status);
                listaAlumnos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en <br>
     * un conjunto de profesores y sus grupos a visualizar en la pagina web.<br>
     * Tambien funciona para hacer el reporte correspondiente de Bitacoras de Profesores
     * <br>
     * 
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    
    public ArrayList<ConsultaBitacorasProfesores> obtenerBitacoraProfesores(){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaBitacorasProfesores> listaDatos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            String sql = "SELECT teachers.id_teacher, teachers.apellido_paterno_teacher, teachers.apellido_materno_teacher, \n" +
                        "teachers.nombre_teacher, grupos.id_group, grupos.id_grade, grade.description_grade , grupos.level_group,  \n" +
                        "grupos.id_category_group, category.description_category, teachers.classroom_teacher FROM teachers \n" +
                        "INNER JOIN grupos ON teachers.id_group_teacher = grupos.id_group\n" +
                        "INNER JOIN grade ON grupos.id_grade = grade.id_grade\n" +
                        "INNER JOIN category ON grupos.id_category_group = category.id_category\n" +
                        "ORDER BY grupos.id_group;";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                int id_teacher = rs.getInt("id_teacher");
                String apellido_paterno_teacher = rs.getString("apellido_paterno_teacher");
                String apellido_materno_teacher = rs.getString("apellido_materno_teacher");
                String nombre_teacher = rs.getString("nombre_teacher");
                int id_group = rs.getInt("id_group");
                int id_grade= rs.getInt("id_grade");
                String description_grade = rs.getString("description_grade");
                int level_group = rs.getInt("level_group");
                int id_category_group= rs.getInt("id_category_group");
                String description_category = rs.getString("description_category");
                String classroom_teacher = rs.getString("classroom_teacher");
                ConsultaBitacorasProfesores consulta = new ConsultaBitacorasProfesores(id_teacher, apellido_paterno_teacher, 
                                            apellido_materno_teacher, nombre_teacher, id_group, id_grade, description_grade, 
                                            level_group, id_category_group, description_category, classroom_teacher);
                listaDatos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaDatos;
    }
    
    /**
     * Este metodo permite hacer una consulta SQL de varias tablas enfocada en <br>
     * un conjunto de Alumnos y sus calificaciones y visualizar su informacion en la pagina web.<br>
     * Tambien funciona para hacer el reporte correspondiente
     * <br>
     * @param id_teacher: Id del profesor a buscar en la base de datos
     * @return ArrayList: Informacion del alumno localizada en diferentes tablas.
     **/
    public ArrayList<ConsultaCalificaciones> obtenerCalificacionesPorGrupo(int id_teacher){
        //1. Obtiene los datos de ingreso del usuario
        ArrayList <ConsultaCalificaciones> listaAlumnos = new ArrayList<>();
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sql = "SELECT students.id_student, students.apellido_paterno_student, students.apellido_materno_student, students.nombre_student, users.nom_user, \n" +
                            "report.id_report, report.first_partial_report, report.second_partial_report, report.avg_report FROM students \n" +
                            "INNER JOIN users ON users.id_user = students.id_user_student \n" +
                            "INNER JOIN report ON report.id_report = students.id_report_student \n" +
                            "WHERE students.id_teacher_student = ? ;";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_teacher);
            rs = pstm.executeQuery();
            
            while(rs.next()){
                //0. Id del Alumno
                int id_student = rs.getInt("id_student");
                //1. Apellido Paterno
                String apellido_paterno = rs.getString("apellido_paterno_student");
                //2. Apellido Materno
                String apellido_materno = rs.getString("apellido_materno_student");
                //3. Nombre
                String nombre = rs.getString("nombre_student");
                //4. Nombre de Usuario
                String nom_user  =rs.getString("nom_user");
                //5. Calificacion de Primer Parcial
                double first_partial = rs.getDouble("first_partial_report");
                //6. Calificacion de Segundo Parcial
                double second_partial = rs.getDouble("second_partial_report");
                //7. Promedio final
                double average = rs.getDouble("avg_report");
                //8. Id de lista de calificaciones
                int id_report = rs.getInt("id_report");
                ConsultaCalificaciones consulta = new ConsultaCalificaciones(id_student, apellido_paterno, apellido_materno, nombre, nom_user,
                                            id_report, first_partial, second_partial, average);
                listaAlumnos.add(consulta);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
        return listaAlumnos;
    }
    
    //Parte 8: Eliminacion de datos
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Users</b>
     * <br>
     * @param id_user: Id del usuario a eliminar en la base de datos
     **/
    public void eliminarUsuario(int id_user){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");

            //Ejecuta la consulta de eliminacion de MySQL
            String sqlEliminar = "DELETE FROM users WHERE id_user = (?);";
           
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_user);
            pstm.executeUpdate();
           
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Admin_School</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_admin: Id del administrador a eliminar en la base de datos
     **/
    public void eliminarAdministrador(int id_admin){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Ejecuta la consulta de eliminacion de MySQL
            String sqlEliminar = "DELETE FROM admin_school WHERE id_admin = (?);";
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_admin);
            pstm.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Teachers</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_teacher: Id del profesor a eliminar en la base de datos
     **/
    public void eliminarTeacher(int id_teacher){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");

            //Ejecuta la consulta de eliminacion de MySQL
            String sqlEliminar = "DELETE FROM teachers WHERE id_teacher = (?);";
            
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_teacher);
            pstm.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Students</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_student: Id del estudiante a eliminar en la base de datos
     **/
    public void eliminarAlumno(int id_student){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sqlEliminar = "DELETE FROM students WHERE id_student = (?);";
            
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_student);
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Admin_School</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_report: Id de la lista de calificaciones a eliminar en la base de datos
     **/
    public void eliminarListaCalificaciones(int id_report){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            
            //Lo busca con respecto al id de usuario de su respectiva tabla
            String sqlEliminar = "DELETE FROM report WHERE id_report = (?);";
            
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_report);
            pstm.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
            ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Payment</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_payment: Id de la lista de Seguimiento a eliminar en la base de datos
     **/
    public void eliminarListaDePago(int id_payment){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");

            //Ejecuta la consulta de eliminacion de MySQL
            String sqlEliminar = "DELETE FROM payment WHERE id_payment = (?);";

            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_payment);
            pstm.executeUpdate();
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite hacer una consulta SQL que permite eliminar un registro
     * de la tabla <b>Grupos</b> e información relacionada en otras tablas. 
     * <br>
     * @param id_group: Id del grupo a eliminar en la base de datos
     **/
    public void eliminarGrupo(int id_group){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            //Ejecuta la consulta de eliminacion de MySQL
            String sqlEliminar = "DELETE FROM grupos WHERE id_group = (?);";
            pstm = con.prepareStatement(sqlEliminar);
            pstm.setInt(1, id_group);
            pstm.executeUpdate();
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    
    //Parte 9: Operaciones de desvinculación de Tablas
    /**
     * Este metodo permite desvincular a los profesores que tangan asignados a un grupo a eliminar 
     * <br>
     * @param id_group: Id del grupo a desvincular en la base de datos
     **/
    public void desvincularProfesores(int id_group){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            
            //A la hora de eliminar grupo, se actualiza teachers ya que esta relacionada
            String sqlActualizar = "UPDATE teachers SET id_group_teacher = null WHERE id_group_teacher = (?)";
            pstm = con.prepareStatement(sqlActualizar);
            pstm.setInt(1, id_group);
            pstm.executeUpdate();
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    /**
     * Este metodo permite desvincular a los alumnos que tengan asignados a un grupo a eliminar 
     * <br>
     * @param id_teacher: Id del profesor a desvincular en la base de datos
     **/
    public void desvincularAlumnos(int id_teacher){
        try{
            String urlDB = "jdbc:mysql://localhost:3306/tallerdeingles?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(urlDB, "nbUser", "123456");
            
            //A la hora de eliminar grupo, se actualiza teachers ya que esta relacionada
            String sqlActualizar = "UPDATE students SET id_teacher_student = null WHERE id_teacher_student = (?)";
            pstm = con.prepareStatement(sqlActualizar);
            pstm.setInt(1, id_teacher);
            pstm.executeUpdate();
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        finally{
            try{
                pstm.close();
                con.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
