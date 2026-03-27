<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import="java.time.*" %>
<%@page import="java.time.format.TextStyle" %>
<%@page import = "jakarta.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=Constantes.TITULO_VISTA_CALIFICACIONES%> </title>
    
    <link rel="stylesheet" href="../css/style_menuprincipal.css" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href = "../Images/uaem.png" rel = "icon"/>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">
</head>

<body>
    <%
        //Obtiene la sesion al usuario principal
        HttpSession sesion = request.getSession();
        String usuarioPrincipal = (String)sesion.getAttribute("sesionIniciada");
        String rangoPrincipal = "";
        String id_userPrincipal = "";
        
        if(usuarioPrincipal == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        else{
            rangoPrincipal = String.valueOf(sesion.getAttribute("rango"));
            id_userPrincipal = String.valueOf(sesion.getAttribute("userId"));
        }
        //Accede a la base de datos y accede a los datos del usuario principal
        BaseDatos base = new BaseDatos();
    %>
    <aside id = "menu_lateral">
        <ul id="menu_opciones">
            <li>
                <img src="../Images/Logo_Taller2.png" alt=""> 
            </li>
            
            <%
                if (rangoPrincipal.equals("ESTUDIANTE")){
                    //Obtiene el atributo del Id de pagos y calificaciones para el resto de la sesion
                    sesion.getAttribute("pagos");
                    sesion.getAttribute("calif");
            %>
            <li>
                <a href="../view/menuAlumno.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/seguimientoPago.jsp">
                    <i class="fa-solid fa-dollar-sign"></i> <br>
                    Seguimiento
                </a>
            </li>

            <li>
                <a href="../view/vistaCalificaciones.jsp" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-school"></i> <br>
                    Calificaciones
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
            <%}else {%>
            <li>
                <a href="../view/menuAdministrador.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/listaAlumnos.jsp">
                   <i class="fa-solid fa-users-line"></i><br>
                    Alumnos
                </a>
            </li>

            <li>
                <a href="../view/listaTeachers.jsp">
                    <i class="fa-solid fa-chalkboard-user"></i>  <br>
                    Maestros
                </a>
            </li>
            
            <li>
                <a href="../view/listaAdministradores.jsp">
                    <i class="fa-brands fa-black-tie"></i><br>
                    Administradores
                </a>
            </li>
            
            <li>
                <a href="../view/listaGrupos.jsp">
                   <i class="fa-solid fa-school"></i><br>
                    Grupos
                </a>
            </li>

            <li>
                <a href="../view/listaDocumentos.jsp">
                    <i class="fa-solid fa-print"></i><br>
                    Documentos
                </a>
            </li>

            <li>
                <a href="../cerrarSesion">
                    <i class="fa-solid fa-right-from-bracket"></i> <br>
                    Cerrar Sesión
                </a>
            </li>
            <%}%>
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
            <div class="form-container2">
                <h1> 
                <i class="fa-solid fa-graduation-cap"></i><br> 
                Calificaciones
                </h1>
                <%
                    LocalDate hoy = LocalDate.now();
                    String mesActual = hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                    mesActual = mesActual.substring(0, 1).toUpperCase() + mesActual.substring(1);
                    String periodoActual = hoy.getYear() + " " + base.obtenerPeriodo(mesActual);
                %>

                <%
                    //Establece los datos del usuario a modificar
                    String idGradeRequest = request.getParameter("idGrade");
                    String idSesion = String.valueOf(sesion.getAttribute("calif"));
                    
                    int usuarioModificar = 0;
                    if(idGradeRequest != null)
                        usuarioModificar = Integer.parseInt(idGradeRequest);
                    else
                        usuarioModificar = Integer.parseInt(idSesion);
                    
                    String status = "";
                    double average = 0.0;
                    String grupo = "";
                    String profesor = "";
                    
                    ArrayList <Report> seguimiento = base.obtenerCalificaciones(usuarioModificar);
                    Iterator <Report> iter = seguimiento.iterator();
                    Report per = null;
                    
                    ArrayList <ConsultaAlumnos> datosAlumno = base.obtenerDatosAlumno(String.valueOf(usuarioModificar));
                    Iterator <ConsultaAlumnos> iterAlumno = datosAlumno.iterator();
                    ConsultaAlumnos perAlumno = null;
                    
                    if(iterAlumno.hasNext()){
                        perAlumno = iterAlumno.next();
                        grupo = base.concatenarDatosGrupo(perAlumno.getId_teacher_student());
                        profesor = base.concatenarDatosGrupo(perAlumno.getId_teacher_student());
                    }
                    String nombreCompleto = base.concatenarDatosAlumno(usuarioModificar);

                    if(iter.hasNext()){
                        per = iter.next();
                        average = (per.getFirst_partial_report() + per.getSecond_partial_report())/2;
                    }
                %>
                <h4> 
                    <%=nombreCompleto%>
                </h4>
                <h2> Periodo Actual </h2>
                <table id = "tablaSeguimiento" class="table table-striped">
                    <thead>
                        <tr>       
                            <th scope="col">Ciclo Escolar</th>
                            <th scope="col">Grupo</th>
                            <th scope="col">Profesor Asignado</th>
                            <th scope="col">Primer Parcial </th>
                            <th scope="col">Segundo Parcial</th>
                            <th scope="col">Promedio</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="col"><%=periodoActual%></th>
                            <th scope="col"><%=grupo%></th>
                            <td scope="col"><%=profesor%></td>
                            <%if (rangoPrincipal.equals("ESTUDIANTE")){%>
                            <td style = "background-color: rgba<%=per.getFirst_partial_report()<6.0 ? "(245, 39, 39, 0.1)" :"(39, 245, 135, 0.1)"%>">
                                <%=per.getFirst_partial_report() %>
                            </td>
                            <td style = "background-color: rgba<%=per.getSecond_partial_report()<6.0 ? "(245, 39, 39, 0.1)" :"(39, 245, 135, 0.1)"%>">
                                <%=per.getSecond_partial_report()%>
                            </td> 
                            <td style = "background-color: rgba<%=average<6.0 ? "(245, 39, 39, 0.1)" :"(39, 245, 135, 0.1)"%>">
                                <%=average%>
                            </td> 
                            <%}
                            else{%>
                            <form class = "form" action = "../updateGrade" method = "post">
                                <input type = "hidden" id = "idGrade" name = "idGrade" value = "<%=usuarioModificar%>"/> 
                                <td>
                                    <input type="number" name="firstPartial" id="firstPartial" step = "0.1" min = "0" max = 10 value ="<%=per.getFirst_partial_report() %>"> 
                                </td>
                                <td>
                                    <input type="number" name="secondPartial" id="secondPartial" step = "0.1" min = "0" max = 10 value ="<%=per.getSecond_partial_report() %>" maxlength = 10> 
                                </td> 
                                <td>
                                    <%=per.getAvg_report()%>
                                </td>
                            
                            <%}%>
                        </tr>
                    </tbody>
                </table>
                <%if(!rangoPrincipal.equals("ESTUDIANTE")){%>
                <%
                    String urlBack = urlBack ="../view/listaAlumnos.jsp";
                %>
                <div class = "options">
                    <div id = "modificar">
                        <button type = "button" name = "update" id ="update" onclick="showAlertUpdate()">
                           <i class="fa-solid fa-arrow-rotate-right"></i><br>
                            Actualizar Calificaciones
                        </button>
                        <button type = "button"  name = "back" id ="back" onclick = "location.href = '<%=urlBack%>'">
                           <i class="fa-solid fa-arrow-left-long"></i><br>
                            Regresar
                        </button> 
                    </div>
                </div>
                </form>    
 
                <%}%>        
                <%if (!rangoPrincipal.equals("PROFESOR")){%>        
                <h2> Historico </h2>
                <table id = "tablaAlumnos" class="table table-striped">
                    <thead>
                        <tr>       
                            <th scope="col">Ciclo Escolar</th>
                            <th scope="col">Grupo</th>
                            <th scope="col">Profesor Asignado</th>
                            <th scope="col">Primer Parcial </th>
                            <th scope="col">Segundo Parcial</th>
                            <th scope="col">Promedio</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <td scope="col"></td>
                            <td></td>
                            <td></td> 
                            <td></td>                            
                        </tr>
                    </tbody>
                </table>
                <%}%>   
            </div>
        </div>
    </article>
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
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