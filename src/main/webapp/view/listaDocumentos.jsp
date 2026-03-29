<%-- 
    Document   : listaDocumentos
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
    <title><%=Constantes.Titulos.TITULO_LISTA_DOCUMENTOS%> </title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_LISTA%>">
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
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>
            
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
                <a href="../view/listaDocumentos.jsp" style = "background-color: rgba(44, 82, 52, 1)">
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
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
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
            %>
            <div class="form-container2">
                <i class="fa-solid fa-print"></i><br>
                <h1>Documentos para Imprimir</h1>
                <table id = "tablaAlumnos" class="table table-striped">
                    <div class = "documents"> 
                        <p>
                            <i class="fa-solid fa-person-circle-question" ></i> <br>
                            Alumnos sin grupo: <%=base.conteoAlumnos(0)%>
                        </p>
                        <a id = 'link' <%=base.conteoProfesores() == 0 ? "style = 'pointer-events: none; background-color:gray'" : "href = '../reportesServlet'  target = '_blank'"%> >
                           <i class="fa-solid fa-clipboard-user"></i> <br>
                            Bitacora de Profesores
                        </a>
                    </div>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Grupo</th>
                            <th scope="col">Profesor</th>
                            <th scope="col">Numero de Alumnos</th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col">Salon de Clases</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        //NOTA: SOLO EL ADMINISTRADOR TIENE ACCESO A LAS LISTAS DE TODO EL PERSONAL
                        if(!rangoPrincipal.equals("ADMINISTRADOR")){
                            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
                            return;
                        }
                        ArrayList <ConsultaBitacorasProfesores> listaTeachers = base.obtenerBitacoraProfesores();
                        Iterator<ConsultaBitacorasProfesores> iter = listaTeachers.iterator();
                        ConsultaBitacorasProfesores per = null;
                        Reportes report = new Reportes();
                        int conteo = 0;
                        while(iter.hasNext()){
                            per = iter.next();
                            conteo++;
                    %>
                        <tr>
                            <th scope="row"><%=conteo%></th>
                            <td><%=base.concatenarDatosGrupo(per.getId_teacher())%></td>
                            <td><%=base.concatenarDatosProfesor(per.getId_teacher())%></td>
                            <td><%=base.conteoAlumnos(per.getId_teacher())%></td>
                            <td> 
                                    
                                <a <%=base.conteoAlumnos(per.getId_teacher()) == 0 ? 
                                        "style = 'pointer-events: none; color:gray'": 
                                        "" %> href = "../reportesServlet?Attendance=<%=per.getId_teacher()%>&c=<%=per.getClassroom_teacher()%>" title="Ver bitacoras de Asistencia" target="_blank">
                                    <i class="fa-solid fa-clipboard-user"></i>
                                </a>
                            </td>
                            <td>
                                <a <%=base.conteoAlumnos(per.getId_teacher()) == 0 ? 
                                        "style = 'pointer-events: none; color:gray'": 
                                        "" %> href = "../reportesServlet?Payment=<%=per.getId_teacher()%>&c=<%=per.getClassroom_teacher()%>" title="Ver lista de Seguimiento de Pago" target="_blank">
                                    <i class="fa-solid fa-dollar-sign"></i>
                                </a>
                            </td>
                            <td>
                                <a <%=base.conteoAlumnos(per.getId_teacher()) == 0 ? 
                                        "style = 'pointer-events: none; color:gray'": 
                                        "" %> href = "../reportesServlet?Grade=<%=per.getId_teacher()%>&c=<%=per.getClassroom_teacher()%>" title="Ver lista de calificaciones" target="_blank">
                                    <i class="fa-solid fa-graduation-cap"></i>
                                </a>  
                            </td>
                            <td>
                                <%=per.getClassroom_teacher()%>
                            </td> 
                        </tr>
                    <%}%>      
                    </tbody>
                </table>
                
  
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