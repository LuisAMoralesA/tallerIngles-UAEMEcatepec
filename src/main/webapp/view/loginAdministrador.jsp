<%-- 
    Document   : loginAdministrador
    Author     : Luis Morales
--%>
<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <meta name = "viewport" content = "width-device-width, initial-scale = 1.0">
        <title> <%=Constantes.Titulos.TITULO_LOGIN_ADMIN%> </title>
        <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
        <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_LOGIN%>">
        <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_FONTAWESOME%>" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!--Librerias para alertas emergentes-->
        <link rel = "stylesheet" href="<%=Constantes.LinksExternos.URL_CSS_SWEETALERT%>">
        <script src = "<%=Constantes.LinksExternos.URL_JS_SWEETALERT%>"></script>
    </head>

    <body class = "admin-body">
        <header class = "header">
            <div class="menu container">
                <a href="https://cuecatepec.uaemex.mx/" class="logo-navbar">
                    <img src="<%=Constantes.Imagenes.URL_UAEM%>" class = "logo-navbar" alt="" >
                </a>
    
                <input type = "checkbox" id = "menu" />
                <label for="menu"> 
                    <img src="<%=Constantes.Imagenes.URL_MENU%>" class = "menu-icono" alt="">
                </label>
    
                <nav class = "navbar">
                    <ul>
                        <li>
                            <a href="<%=Constantes.VentanasJSP.URL_INDEX%>"> Inicio </a>  
                        </li>
                        <li>
                            <a href="<%=Constantes.VentanasJSP.URL_LOGIN_ALUMNO%>"> Alumnos </a>  
                        </li>
                        <li> 
                            <a href="<%=Constantes.VentanasJSP.URL_LOGIN_TEACHER%>"> Profesores </a>  
                        </li>
                        <li> 
                            <a href="<%=Constantes.VentanasJSP.URL_LOGIN_ADMIN%>" style = "color: rgb(255, 213, 1)"> Administrativos </a>  
                        </li>
                    </ul>
                </nav>
            </div>
        </header>

        <main>
            <br><br>
            <div class="contenedor_login_register1">
                <%//Este apartado usa el servlet loginAdmin%>
                <form method = "POST" class = "form_login1" action = "<%=Constantes.Servlets.SERVLET_SESION_ADMIN%>">
                    <h2>
                        <i class="fa-solid fa-user-tie"></i> <br>
                        Bienvenido, Administrador 
                    </h2>
                    <p>Ingrese sus datos para iniciar Sesión</p>
                    <input type = "text" id="user" name="user"placeholder="Usuario">
                    <input type = "password" id="pass" name="pass" placeholder="Contraseña">
                    <button type ="submit" id= "submit" name="submit"> Ingresar </button>
                </form>
                <<%
                    //Obtiene la sesion que se encuentra en el navegador
                    HttpSession sesion = request.getSession();
                    //Busca si se trato de autenticarse pero no coincidian los datos
                    String errorMensaje = (String) sesion.getAttribute("errorMessage");
                    //Si esta accion es detectada, se abre el cuadro de texto diciendo el error de autenticación
                    if (errorMensaje != null && !errorMensaje.isEmpty()){
                %>
                    <script>
                        Swal.fire({
                            icon: "error",
                            title: "Oops...",
                            text: "<%= errorMensaje %>",
                            confirmButtonColor: "#9C8412"
                          });
                    </script>
                    <%}
                    //Establece que no hay mensaje de error
                    sesion.setAttribute("errorMessage", null);
                    %>
            </div>
        </main>

        
        <footer class="footer" > 
        <div class="ubicacion">
            <p class ="p-mapa">
            <i class="fas fa-map-marker-alt"></i>
                <b> Calle:  </b> Jose Revueltas No. 17
                <br>
                <b>Colonia: </b> Tierra Blanca
                <br>
                Ecatepec de Morelos
                <br>
                <i>55020</i>
            </p>
        </div>
        

        <div class="links">
            <ul>
                <li>
                    <a href="https://www.facebook.com/TallerdeInglesEcatepec" target="_blank" rel="noopener noreferrer">
                        <i class="fa-brands fa-facebook"></i>&nbsp; 
                        Taller de Ingles para Niños y Adolescentes 
                    </a>
                </li>

                <li>
                    <a href="mailto:tallerdeinglesuaem@gmail.com">
                        <i class="fa-solid fa-envelope"></i>&nbsp; 
                        tallerdeinglesuaem@gmail.com
                    </a>         
                </li>
            </ul>
        </div>
    </footer>
    </body>
</html>
