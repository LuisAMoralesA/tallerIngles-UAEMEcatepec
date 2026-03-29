<%-- 
    Document   : actualizarUsuario
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
    <title><%=Constantes.Titulos.TITULO_ACTUALIZAR_USUARIO%> </title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_ACTUALIZAR_USER%>">
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_FONTAWESOME%>" 
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_BOOTSTRAP%>"  
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!--Librerias para alertas emergentes-->
    <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_SWEETALERT%>" >
    <script src="<%=Constantes.LinksExternos.URL_JS_SWEETALERT%>"></script>
</head>

<body>
    <%
        
        int id_user = 0; 
        String username = "";
        String password = "";  
        String rango = "";
        
        //Obtiene la sesion al usuario
        HttpSession sesion = request.getSession();
        String idUserRequest = request.getParameter("idUser");
        String usuario = (String) sesion.getAttribute("sesionIniciada");
        
        if(usuario == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        String rangoPrincipal = String.valueOf(sesion.getAttribute("rango"));
        String id_userPrincipal = String.valueOf(sesion.getAttribute("userId"));
        String url = "";
        
        String usuarioModificar = "";
        if(idUserRequest != null)
            usuarioModificar = idUserRequest;
        else
            usuarioModificar = usuario;

        //Accede a la base de datos y accede a los datos del usuario
        BaseDatos base = new BaseDatos();
        
        ArrayList <Users> lista = base.obtenerUsuario(usuarioModificar);
        Iterator<Users> iter = lista.iterator();
        Users per = null;
        
        if(iter.hasNext()){
            per = iter.next();
            id_user = per.getId_user();
            username = per.getNom_user();
            password = per.getPassword();
            rango = per.getRango();
            switch(rangoPrincipal){
                case "ESTUDIANTE":
                    url = "../view/menuAlumno.jsp";
                    break;
                case "PROFESOR":
                    url = "../view/menuTeacher.jsp";
                    break;
                case "ADMINISTRADOR":
                    switch(rango){
                        case "ESTUDIANTE":
                            url = "../view/listaAlumnos.jsp";
                            break;
                        case "PROFESOR":
                            url = "../view/listaTeachers.jsp";
                            break;
                        default:
                            url = "../view/menuAdministrador.jsp";
                            break;
                   }
                   break;
            }
        }
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
                <a href="../view/vistaCalificaciones.jsp">
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
            <%}
                else if (rangoPrincipal.equals("PROFESOR")){
                    sesion.getAttribute("gruposId");
            %>
            <li>
                <a href="../view/menuTeacher.jsp">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>


            <li>
                <a href="../view/actualizarCalificaciones.jsp">
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
            <%}
            else {%>
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
        <%
                String mensaje = (String) sesion.getAttribute("contraseñaIncorrecta");
                if (mensaje != null && !mensaje.isEmpty()){
            %>
                <script>
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "<%= mensaje %>",
                        confirmButtonColor: "#9C8412"
                      });
                </script>
                <%}
                sesion.setAttribute("contraseñaIncorrecta", null);%>
        <div id = "perfil_usuario">
            <div class="form-container">
                <form action="../updateUser" method="post">
                <h1> 
                    <i class="fa-solid fa-arrows-rotate"></i><br> 
                    Actualizar Datos de Inicio de Sesión 
                </h1>

                <input type = "hidden" name = "iduser" id="iduser" value = "<%=id_user%>">
                <label for="username">
                    Nombre de Usuario: <%=rangoPrincipal.equals(rango) || id_userPrincipal.equals(idUserRequest)? username : ""%>
                </label>
                <input type="<%=rangoPrincipal.equals(rango) || id_userPrincipal.equals(idUserRequest) ? "hidden" : "text"%>" name="username" id="username" required value = "<%=username%>">

                <label for="password1">
                    Contraseña: 
                </label>
                <input type="password" name="password1" id="password1" required value = "">

                <label for="password2">
                    Confirmar Contraseña: 
                </label>
                <input type="password" name="password2" id="password2" required value = "">
                <input type = "hidden" name = "rango" id="rango" value = "<%=rango%>">
                
                <div id = "button">
                    <div id = "modificar"> 
                        <button type = "button" name = "update" id ="update" onclick = "showAlertUpdate()">
                            <i class="fa-solid fa-pen"></i><br>
                            Actualizar Datos
                        </button>
                        <button type = "button" name = "back" id ="back" onclick = "location.href = '<%=url%>'">
                            <i class="fa-solid fa-arrow-left-long"></i><br>
                            Regresar
                        </button>
                    </div>
                </div>
            </form>
            </div>
        </div>
    </article>
    <script src = "/tallerDeInglesUAEM/js/mensajesEmergentes.js"></script>
</body>

</html>