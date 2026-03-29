<%-- 
    Document   : menuTeacher
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
    <title><%=Constantes.Titulos.TITULO_MENU_TEACHER%></title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_TEACHER%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
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
       //Obtiene la sesion al usuario
        HttpSession sesion = request.getSession();
        String usuario = (String) sesion.getAttribute("sesionIniciada");
        if(usuario == null){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        //Accede a la base de datos y accede a los datos del usuario
        BaseDatos base = new BaseDatos();
        ArrayList <ConsultaTeacher> lista = base.obtenerDatosProfesores(usuario);
        Iterator<ConsultaTeacher> iter = lista.iterator();
        ConsultaTeacher per = null;
        //Inicializa cadenas de texto para guardar datos concatenados
        String nombreCompleto = "";
        String grupo = "";
        String nombreUser = "";
        String telefono = "";
        String rangoPrincipal = "";
        String classroom = "";
    %>
    <aside id = "menu_lateral">
        <% 
        if(iter.hasNext()){
            per = iter.next();
            //FUNCIONES DE CONCATENAR STRINGS Y COLOCAR TEXTO EN FORMS
            nombreCompleto = base.concatenarDatosProfesor(per.getId_teacher());
            grupo = base.concatenarDatosGrupo(per.getId_group_teacher());
            nombreUser = per.getNom_user();
            telefono = per.getTelefono_teacher();
            rangoPrincipal = per.getRango();
            
            //** --> ATRIBUTOS DEL USUARIO PRINCIPAL
            //Obtiene el atributo del Id_grupo para la impresion de documentos y calificaciones
            sesion.setAttribute("gruposId", per.getId_teacher());
            //Obtiene el atributo del Id_User para el resto de la sesion
            sesion.setAttribute("rango", per.getRango());
            //Obtiene el atributo del Id_User para el resto de la sesion
            sesion.setAttribute("userId", per.getId_user());
            //Obtiene el atributo del modificar datos personales para el resto de la sesion
            sesion.setAttribute("modificar", per.getId_user());
            //Obtiene el atributo del Rango para el resto de la sesion
            sesion.setAttribute("rangoModificar", per.getRango());
            //Obtiene el atributo de salon de clases
            sesion.setAttribute("classroom", per.getClassroom());
        }
        %>
        <ul id="menu_opciones">
            <li>
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>

            <li>
                <a href="../view/menuTeacher.jsp" style = "background-color: rgba(44, 82, 52, 1)">
                    <i class="fa-solid fa-circle-user"></i> <br>
                        Cuenta
                </a>
            </li>

            <li>
                <a href="../view/asignarCalificaciones.jsp">
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
        //NOTA: SOLO EL ADMINISTRADOR TIENE ACCESO A LAS LISTAS DE TODO EL PERSONAL
        if(!rangoPrincipal.equals("PROFESOR")){
            response.sendRedirect("/tallerDeInglesUAEM/view/sesionExpirada.jsp");
            return;
        }
        %>
    <article >
        <div id = "perfil_usuario">
            <h1> Perfil de Profesor </h1>
            <div id = "imagen_perfil">
                <img src="<%=Constantes.Imagenes.URL_USER2%>" alt=""> 
            </div>
            
            <div id = "datos_personales">
                <h2> Datos Personales </h2>
                <p> <b>Nombre completo: </b> <%=nombreCompleto%></p>
                <p> <b>Nombre de Usuario: </b> <%=nombreUser%></p>
                <p> <b>Numero de telefono principal: </b> <%=telefono%></p>
                <h2> Datos Academicos </h2>
                <p> <b>Rango: </b> <%=rangoPrincipal%></p>
                <p> <b>Grupo Asignado: </b> <%=grupo%></p>
            </div>

            <div id = "button">
            <ul id = "modificar">
                <li>
                    <a href="../view/actualizarInformacion.jsp">
                        <i class="fa-solid fa-pen"></i> <br>
                        Modificar Datos
                    </a>
                </li>

                <li>
                    <a href="../view/actualizarUsuario.jsp">
                        <i class="fa-solid fa-key"></i> <br>
                        Cambiar Contraseña
                    </a>
                </li>
            </ul>
        </div>
        </div>
    </article>
</body>
</html>
