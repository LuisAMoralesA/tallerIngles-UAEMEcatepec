<%-- 
    Document   : sesionExpirada
    Author     : Luis Morales
--%>
<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page import ="controller.*"%>
<%@page import ="jakarta.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang = "es">
    <head>
        <meta charset = "UTF-8">
        <meta name = "viewport" content = "width-device-width, initial-scale = 1.0">
        <title> <%=Constantes.Titulos.TITULO_SESION_EXPIRADA%> </title>
        <link href = "<%=Constantes.Imagenes.URL_LOGO_TALLER2%>" rel = "icon"/>
        <link rel = "stylesheet" href = "<%=Constantes.EstilosCSS.URL_CSS_SESION_EXPIRADA%>">
        <link rel = "stylesheet" href = "<%=Constantes.LinksExternos.URL_CSS_FONTAWESOME%>" 
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" 
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!--Librerias para alertas emergentes-->
        <link rel = "stylesheet" href="<%=Constantes.LinksExternos.URL_CSS_SWEETALERT%>">
        <script src = "<%=Constantes.LinksExternos.URL_JS_SWEETALERT%>"></script>
    </head>

    <body class = "body-expired">
        <header class = "header">
            <div class="menu1 container">
                <img src="<%=Constantes.Imagenes.URL_LOGO_TALLER1%>" class = "logo-sesion" alt="" >
            </div>
        </header>

        <main>
            <div class = "sesionExpirada">
                <%
                    //Obtiene la sesion al usuario principal
                    HttpSession sesion = request.getSession();
                    String rangoPrincipal = (String) sesion.getAttribute("rango");
                    String link = "../index.html";
                    String textButton = "Volver a Inicio";
                    if(rangoPrincipal != null){
                        textButton = "Volver a la sesion";
                        switch(rangoPrincipal){
                            case "ESTUDIANTE":
                                link = "../view/menuAlumno.jsp";
                                break;
                            case "PROFESOR":
                                link = "../view/menuTeacher.jsp"; 
                                break;
                            case "ADMINISTRADOR":
                                link = "../view/menuAdministrador.jsp"; 
                                break;
                        }
                    }
                %>
                <img class ="img2" src="<%=Constantes.Imagenes.URL_SESION_EXPIRADA%>" alt="alt"/>
                <h1> Pagina no encontrada </h1>
                <b><p> Lo sentimos, no se puede acceder al URL deseado. </p></b>
                <a href=<%=link%>> <%=textButton%> </a>
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
    <script src = "/tallerDeInglesUAEM/js/login.js"></script>
    </body>
</html>