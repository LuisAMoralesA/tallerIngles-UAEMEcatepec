<%-- 
    Document   : agregarInformacion
    Author     : Luis Morales
--%>
<%@page import ="java.sql.*" %>
<%@page import ="com.mysql.cj.jdbc.Driver" %>
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
    <title><%=Constantes.Titulos.TITULO_AGREGAR_INFORMACION%> </title>
    <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_MENU_OPCIONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_BOTONES%>">
    <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_AGREGAR_USER%>">
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
            response.sendRedirect(Constantes.VentanasJSP.URL_SESION_EXPIRADA);
            return;
        }
        //Accede a la base de datos y accede a los datos del usuario
        BaseDatos base = new BaseDatos();
        ArrayList <ConsultasAdmin> lista = base.obtenerDatosAdministrador(usuario);
        Iterator<ConsultasAdmin> iter = lista.iterator();
        ConsultasAdmin per = null;
        //Inicializa cadenas de texto para guardar datos concatenados
        String nombreCompleto = "";
    %>
    <aside id = "menu_lateral">
        <% 
        if(iter.hasNext()){
            per = iter.next();
            //FUNCIONES DE CONCATENAR STRINGS
            nombreCompleto = base.concatenarDatosAdministrador(per.getId_admin());
            
            //** --> ATRIBUTOS DEL USUARIO PRINCIPAL
            //Obtiene el atributo del Rango para el resto de la sesion
            sesion.setAttribute("rango", per.getRango());
            //Obtiene el atributo del Id_User para el resto de la sesion
            sesion.setAttribute("userId", per.getId_user());
            //Obtiene el atributo del modificar datos personales para el resto de la sesion
            sesion.setAttribute("modificar", per.getId_user());
            //Obtiene el atributo del Rango para el resto de la sesion
            sesion.setAttribute("rangoModificar", per.getRango());
        }
        %>
        <ul id="menu_opciones">
            <li>
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" alt=""> 
            </li>
            <%@include file="../menuLateral/menuLateralAdministradores.jsp"%>
        </ul>
    </aside>

    <article >
        <%@include file="definirInformacion.jsp" %>
        <div id = "perfil_usuario">
            <%@include file="../sweetAlert/ventanaExito.jsp" %>
            <div class="form-container1">
                <form class = "form_register" action="<%=accion%>" method="post">
                    
                    <div class ="titulo-form">
                        <h1> 
                        <i class="fa-solid fa-person-circle-plus"></i><br> 
                        <%=encabezado%>
                        </h1>
                        <b>Favor de llenar los campos para agregar en la base de datos. </b>
                    </div>
                        
                    <input type = "hidden" name = "rango" id="rango" value = "<%=rango%>">
                    <div>
                        <%if (!opcion.equals("4")){%>
                        <!--Apellido Paterno-->
                        <label for = "apaterno">  Apellido Paterno: </label>
                        <input type = "text" name = "apaterno" id = "apaterno" placeholder="Apellido Paterno" required>
                        
                        <!--Apellido Materno-->
                        <label for = "amaterno"> Apellido Materno: </label>
                        <input type = "text" name = "amaterno" id = "amaterno" placeholder="Apellido Materno" required>
                        
                        <!--Nombre(s)-->
                        <label for = "name"> Nombre (s):</label>
                        <input type = "text" name = "name" id = "name" placeholder="Nombre" required>
                        
                        <!--Fecha de nacimiento-->
                        <label for = "birthdate"> Fecha de Nacimiento:</label>
                        <input type = "date" name = "birthdate" id = "birthdate" title="Fecha de nacimiento" class = "date" required>
                        
                        <!--Telefono principal-->
                        <label for = "phone"> Numero de Telefono:</label>
                        <input type = "text" name = "phone" id = "phone" placeholder="Numero de Telefono" maxlength = 10 required>
                        
                        <!--Correo electronico-->
                        <label for = "email"> Correo Electronico:</label>
                        <input type = "text" name = "email" id = "email" placeholder="Correo Electronico" required>
                        <%}
                            else {
                        %>
                        <%@include file="agregarGrupo.jsp" %>
                        <%}%>
                    </div>
                    
                    <div>
                        <%if (!opcion.equals("4")){%>
                            <%if (opcion.equals("1")){%>
                            <%@include file="agregarAlumno.jsp" %>
                            <%}else if(opcion.equals("2")){%>
                            <%@include file="agregarProfesor.jsp" %>
                        <%}%>
                    </div>
                    <br>    
                    <%@include file="registrarContraseña.jsp" %>
                    <%}%>
                <div id = "button">
                    <div id = "modificar"> 
                        <button type = "button" name = "add" id ="add" onclick = "showAlertAdd()">
                            <i class="fa-solid fa-pen"></i><br>
                            Agregar Datos
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
    <script src = "<%=Constantes.JavaScript.URL_JS_MENSAJES_EMERGENTES%>"></script>
</body>
</html>
