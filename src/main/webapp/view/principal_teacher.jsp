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
    <link href = "../Images/uaem.png" rel = "icon"></link>
    <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
    <title>Control Escolar de Profesor</title>
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
                <img src="../Images/Logo_Taller2.png" alt=""> 
            </li>

            <li>
                <a href="../view/principal_teacher.jsp" style = "background-color: rgba(44, 82, 52, 1)">
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
                <img src="../Images/user2.png" alt="">
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
                    <a href="../view/viewPersonalInfo.jsp">
                        <i class="fa-solid fa-pen"></i> <br>
                        Modificar Datos
                    </a>
                </li>

                <li>
                    <a href="../view/viewUserInfo.jsp">
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
