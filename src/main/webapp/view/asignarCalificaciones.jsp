<%-- 
    Document   : asignarCalificaciones
    Author     : Luis Morales
--%>
<%@page import ="java.sql.*" %>
<%@page import ="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="model.Consultas.*"%>
<%@page import ="model.Tables.*"%>
<%@page import ="java.util.*"%>
<%@page import ="jakarta.servlet.http.HttpSession"%>
<%@page contentType ="text/html" pageEncoding ="UTF-8"%>
<%@page session ="true"%>
<!DOCTYPE html>
<html lang = "es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=Constantes.Titulos.TITULO_ASIGNAR_CALIFICACIONES%></title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_ASIGNAR_CALIFICACIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_FONTAWESOME%>" 
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_BOOTSTRAP%>"  
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!--Librerias para alertas emergentes-->
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_SWEETALERT%>" >
    <script src="<%=Constantes.LinksExternos.URL_JS_SWEETALERT%>"></script>
    <link rel="stylesheet" href="<%=Constantes.LinksExternos.URL_CSS_DATATABLES%>">
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
            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
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
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_MENU_TEACHER%>">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>


            <li>
                <a href="<%=Constantes.VentanasJSP.URL_ASIGNAR_CALIFICACIONES%>" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-school"></i> <br>
                    Información del grupo
                </a>
            </li>

            <li>
                <a href="<%=Constantes.Servlets.SERVLET_CERRAR_SESION%>">
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
            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
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
                                <a href = "<%=Constantes.VentanasJSP.URL_VISTA_CALIFICACIONES%>?idGrade=<%=per.getId_report()%>" title="Calificaciones">
                                    <i class="fa-solid fa-graduation-cap"></i>
                                </a>  
                            </td>
                        </tr>
                    <%}%>      
                    </tbody>
                </table>
                <div class = "documents"> 
                    <a id = "link" <%=base.conteoAlumnos(Integer.parseInt(id_teacher)) == 0 ? 
                            "style = 'pointer-events: none; background-color:gray'": 
                            "href = ' " + Constantes.Servlets.SERVLET_GENERAR_REPORTES + "?Attendance=" + Integer.parseInt(id_teacher) + "&c=" + classroom + "'"%>
                            title="Ver lista de Seguimiento de Pago" target="_blank">
                       <i class="fa-solid fa-print"></i> <br>
                        Imprimir Bitacora de Asistencia
                    </a>

                    <a id = "link" <%=base.conteoAlumnos(Integer.parseInt(id_teacher)) == 0 ? 
                            "style = 'pointer-events: none; background-color:gray'": 
                            "href = ' " + Constantes.Servlets.SERVLET_GENERAR_REPORTES + "?Grade=" + Integer.parseInt(id_teacher) + "&c=" + classroom + "'"%>
                            title="Ver lista de Calificaciones" target="_blank">
                      <i class="fa-solid fa-star"></i> <br>
                        Imprimir Lista de Calificaciones
                    </a>
                </div>
                
            </div>
        </div>
    </article>
    <script src="<%=Constantes.LinksExternos.URL_JS_JQUERY%>"></script>
    <script src="<%=Constantes.LinksExternos.URL_JS_JQUERY_DATATABLES%>"></script>
    <script src="<%=Constantes.LinksExternos.URL_JS_DATATABLES%>"></script>
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
