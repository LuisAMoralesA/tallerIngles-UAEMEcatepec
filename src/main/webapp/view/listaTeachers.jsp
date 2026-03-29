<%-- 
    Document   : listaGrupos
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
    <title><%=Constantes.Titulos.TITULO_LISTA_TEACHERS%> </title>
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
        
        if(usuarioPrincipal == null){
            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
            return;
        }
        
        String rangoPrincipal = String.valueOf(sesion.getAttribute("rango"));
        String id_userPrincipal = String.valueOf(sesion.getAttribute("userId"));
        //Accede a la base de datos y accede a los datos del usuario principal
        BaseDatos base = new BaseDatos();
    %>
    <aside id = "menu_lateral">
        <ul id="menu_opciones">
            <li>
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>
            
            <li>
                <a href="<%=Constantes.VentanasJSP.URL_MENU_ADMIN%>">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_LISTA_ALUMNOS%>">
                   <i class="fa-solid fa-users-line"></i><br>
                    Alumnos
                </a>
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_LISTA_TEACHERS%>" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-chalkboard-user"></i>  <br>
                    Maestros
                </a>
            </li>
            
            <li>
                <a href="<%=Constantes.VentanasJSP.URL_LISTA_ADMIN%>">
                    <i class="fa-brands fa-black-tie"></i><br>
                    Administradores
                </a>
            </li>
            
            <li>
                <a href="<%=Constantes.VentanasJSP.URL_LISTA_GRUPOS%>" >
                   <i class="fa-solid fa-school"></i><br>
                    Grupos
                </a>
            </li>

            <li>
                <a href="<%=Constantes.VentanasJSP.URL_LISTA_DOCUMENTOS%>">
                    <i class="fa-solid fa-print"></i><br>
                    Documentos
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
                sesion.setAttribute("userNameRegistrado", null);
            %>
            <div class="form-container2">
                <i class="fa-solid fa-chalkboard-user"></i> <br>
                <h1>Lista de Profesores</h1>
                <table id = "tablaAlumnos" class="table table-striped">
                    <div class = "documents"> 
                        <a id = "link" href="<%=Constantes.VentanasJSP.URL_AGREGAR_INFORMACION%>?add=2">
                           <i class="fa-solid fa-user-plus"></i> <br>
                            Agregar Profesor
                        </a>
                    </div>
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nombre Completo</th>
                            <th scope="col">Telefono Principal</th>
                            <th scope="col">Nombre de Usuario</th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        //NOTA: SOLO EL ADMINISTRADOR TIENE ACCESO A LAS LISTAS DE TODO EL PERSONAL
                        if(!rangoPrincipal.equals("ADMINISTRADOR")){
                            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
                            return;
                        }
                        ArrayList <ConsultaTeacher> listaTeachers = base.obtenerDatosProfesores();
                        Iterator<ConsultaTeacher> iter = listaTeachers.iterator();
                        ConsultaTeacher per = null;
                        int conteo = 0;
                        while(iter.hasNext()){
                            conteo++;
                            per = iter.next();
                            int id_user_teacher = per.getId_user();
                            int id_teacher = per.getId_teacher();
                            String apellidoPaterno = per.getApellido_paterno_teacher();
                            String apellidoMaterno = per.getApellido_materno_teacher();
                            String nombre = per.getNombre_teacher();
                            String telefono = per.getTelefono_teacher();
                            String usuario = per.getNom_user();
                    %>
                        <tr>
                            <th scope="row"><%=conteo%></th>
                            <td><%=base.concatenarDatosProfesor(id_teacher)%></td>
                            <td><%=telefono%></td>
                            <td><%=usuario%></td>
                            <td>
                                <a href = "<%=Constantes.VentanasJSP.URL_ACTUALIZAR_INFORMACION%>?idUser=<%=id_user_teacher%>" title="Modificar Datos Personales">
                                    <i class="fa-solid fa-pen-clip"></i>
                                </a>
                            </td>
                            <td>
                                <a href = "<%=Constantes.VentanasJSP.URL_ACTUALIZAR_USUARIO%>?idUser=<%=id_user_teacher%>" title="Modificar Datos de Acceso">
                                    <i class="fa-solid fa-key"></i>
                                </a>
                            </td> 
                            <td>
                                <form action = "<%=Constantes.Servlets.SERVLET_ELIMINAR_INFORMACION%>?rango=t&id=<%=id_teacher%>&user=<%=id_user_teacher%>" method = "post">
                                    <button type = "button" id ="del" title="Eliminar Profesor" onclick = "showAlertDelete()">
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
    <script src = "<%=Constantes.JavaScript.URL_JS_MENSAJES_EMERGENTES%>"></script>
</body>

</html>