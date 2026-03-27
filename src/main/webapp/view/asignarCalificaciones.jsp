<%-- 
    Document   : principal_students
    Created on : 22 abr. 2025, 21:05:47
    Author     : Luis Morales
--%>

<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import = "jakarta.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel = "stylesheet" href = "../css/style_menuprincipal.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href = <%=Constantes.URL_LOGO_TALLER2%> rel = "icon"/>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
    <title><%=Constantes.TITULO_ASIGNAR_CALIFICACIONES%></title>
</head>
<body>
    <%
       //Obtiene la sesion al usuario
        //Obtiene la sesion al usuario principal
        HttpSession sesion = request.getSession();
        String usuarioPrincipal = (String)sesion.getAttribute("sesionIniciada");
        String rangoPrincipal = "";
        String id_userPrincipal = "";
        String id_teacher = "";
        
        if(usuarioPrincipal == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        else{
            rangoPrincipal = String.valueOf(sesion.getAttribute("rango"));
            id_userPrincipal = String.valueOf(sesion.getAttribute("userId"));
            id_teacher = String.valueOf(sesion.getAttribute("gruposId"));
        }
        //Accede a la base de datos y accede a los datos del usuario principal
        BaseDatos base = new BaseDatos();
    %>
    <aside id = "menu_lateral">
        <ul id="menu_opciones">
            <li>
                <img src="../Images/Logo_Taller2.png" alt=""> 
            </li>

            <li>
                <a href="../view/menuTeacher.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/actualizarCalificaciones.jsp" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-school"></i> <br>
                    Información del grupo
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
        </ul>
    </aside>
    <%
        String mensaje = (String) sesion.getAttribute("actualizacionCompleta");
        if (mensaje != null && !mensaje.isEmpty()){
    %>
        <script>
            Swal.fire({
                icon: "success",
                title: "<%= mensaje %>",
                draggable: true
            });
        </script>
        <%}
        sesion.setAttribute("actualizacionCompleta", null);
        if(!rangoPrincipal.equals("PROFESOR")){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }%>
    <article >
        <div id = "perfil_usuario">
            <div class="form-container2">
                <i class="fa-solid fa-people-group"></i> <br>
                <h1>Lista de Estudiantes</h1>
                <table id = "tablaAlumnos" class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Apellido Paterno</th>
                            <th scope="col">Apellido Materno</th>
                            <th scope="col">Nombre (s)</th>
                            <th scope="col">Primer Parcial</th>
                            <th scope="col">Segundo Parcial</th>
                            <th scope="col">Promedio</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        String classroom = (String) sesion.getAttribute("classroom");
                        ArrayList <ConsultaCalificaciones> listaAlumnos = base.obtenerCalificacionesPorGrupo(Integer.parseInt(id_teacher));
                        Iterator<ConsultaCalificaciones> iter = listaAlumnos.iterator();
                        ConsultaCalificaciones per = null;
                        while(iter.hasNext()){
                            per = iter.next();
                    %>
                        <tr id = "register">
                            <th scope="row"><%=per.getId_student()%></th>
                            <td><%=per.getApellido_paterno_student()%></td>
                            <td><%=per.getApellido_materno_student()%></td>    
                            <td><%=per.getNombre_student()%></td>
                            <td><%=per.getFirst_partial_report()%></td>
                            <td><%=per.getSecond_partial_report()%></td>
                            <td><%=per.getAverage_report()%></td>
                            <td>
                                <a href = "../view/vistaCalificaciones.jsp?idGrade=<%=per.getId_report()%>" title="Calificaciones">
                                    <i class="fa-solid fa-graduation-cap"></i>
                                </a>  
                            </td>
                        </tr>
                    <%}%>      
                    </tbody>
                </table>
                <div class = "documents"> 
                    <a id = "link" <%=base.conteoAlumnos(Integer.parseInt(id_teacher)) == 0 ? "disabled": "" %>href = "../reportesServlet?Attendance=<%=Integer.parseInt(id_teacher)%>&c=<%=classroom%>" title="Ver lista de Seguimiento de Pago" target="_blank">
                       <i class="fa-solid fa-print"></i> <br>
                        Imprimir Bitacora de Asistencia
                    </a>

                    <a id = "link" <%=base.conteoAlumnos(Integer.parseInt(id_teacher)) == 0 ? "disabled": "" %>href = "../reportesServlet?Grade=<%=Integer.parseInt(id_teacher)%>&c=<%=classroom%>" title="Ver lista de Calificaciones" target="_blank">
                      <i class="fa-solid fa-star"></i> <br>
                        Imprimir Lista de Calificaciones
                    </a>
                </div>
                
            </div>
        </div>
    </article>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>
    <script>
    $(document).ready(function() {
        $('#tablaAlumnos').DataTable({
            "pageLength": 9, 
            "responsive": true,
            "lengthChange": false,
            "language": {
                "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
            }
        });
    });
    </script>
</body>
</html>
