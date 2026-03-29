<%-- 
    Document   : listaAlumnos
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
    <title><%=Constantes.Titulos.TITULO_LISTA_ALUMNOS%> </title>
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
                <a href="../view/listaAlumnos.jsp" style = "background-color: rgba(44, 82, 52, 1)">
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
        </ul>
    </aside>
    <article>
        <div id = "perfil_usuario">
            <%
                String mensaje = (String) sesion.getAttribute("actualizacionCompleta");
                String mensajeUsuario = (String) sesion.getAttribute("userNameRegistrado");

                if (mensaje != null && !mensaje.isEmpty()){
            %>
                <script>
                    Swal.fire({
                        icon: "success",
                        title: "<%= mensaje %>",
                        text: "<%= mensajeUsuario %>",
                        draggable: true
                      });
                </script>
                <%}
                sesion.setAttribute("actualizacionCompleta", null);
            %>
            <div class="form-container2">
                <i class="fa-solid fa-people-group"></i> <br>
                <h1>Lista de Estudiantes</h1>
                <table id = "tablaAlumnos" class="table table-striped">
                    <div class = "documents"> 
                        <a id = "link" href="../view/agregarInformacion.jsp?add=1">
                           <i class="fa-solid fa-user-plus"></i> <br>
                            Agregar Alumno
                        </a>
                    </div>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nombre Completo</th>
                            <th scope="col">Telefono Principal</th>
                            <th scope="col">¿Sale Solo?</th>
                            <th scope="col">Nombre de Usuario</th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        //NOTA: SOLO EL ADMINISTRADOR TIENE ACCESO A LAS LISTAS DE TODO EL PERSONAL
                        if(!rangoPrincipal.equals("ADMINISTRADOR")){
                            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
                            return;
                        }
                        ArrayList <ConsultaAlumnos> listaAlumnos = base.obtenerDatosAlumnos();
                        Iterator<ConsultaAlumnos> iter = listaAlumnos.iterator();
                        ConsultaAlumnos per = null;
                        int conteo = 0;
                        while(iter.hasNext()){
                            per = iter.next();
                            conteo++;
                            int id_student  = per.getId_student();
                            int id_user = per.getId_user_student();
                            int id_payment = per.getId_payment_student();
                            int id_report = per.getId_report_student();
                            String usuario = per.getNom_user();
                            String telefono = per.getTelefono1_student();
                            boolean sale_solo = per.isSale_solo();
                    %>
                        <tr id = "register">
                            <th scope="row"><%=conteo%></th>
                            <td><%=base.concatenarDatosAlumno(id_student)%></td>
                            <td><%=telefono%></td>
                            <td style = "background-color: rgba<%=sale_solo==false ? "(245, 39, 39, 0.1)" :"(39, 245, 135, 0.1)"%>">
                                <%=sale_solo==true ? "SI" :"NO"%>
                            </td>
                            <td><%=usuario%></td>
                            <td>
                                <a href = "../view/actualizarInformacion.jsp?idUser=<%=id_user%>" title="Modificar Datos Personales">
                                    <i class="fa-solid fa-pen-clip"></i>
                                </a>
                            </td>
                            <td>
                                <a href = "../view/actualizarUsuario.jsp?idUser=<%=id_user%>" title="Modificar Datos de Acceso">
                                    <i class="fa-solid fa-key"></i>
                                </a>
                            </td> 
                            <td>
                                <a href = "../view/seguimientoPago.jsp?idPay=<%=id_payment%>" title="Seguimiento de Pago">
                                    <i class="fa-solid fa-dollar-sign"></i>
                                </a>
                            </td>
                            <td>
                                <a href = "../view/vistaCalificaciones.jsp?idGrade=<%=id_report%>" title="Calificaciones">
                                    <i class="fa-solid fa-graduation-cap"></i>
                                </a>  
                            </td>
                            <td>
                                <form action = "../deleteInformation?rango=s&id=<%=id_student%>&user=<%=id_user%>" method = "post">
                                    <button type =  "button" id ="del" title="Eliminar Alumno" onclick = "showAlertDelete()">
                                        <i class="fa-solid fa-x"></i>
                                    </button> 
                                </form>
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
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
</body>

</html>