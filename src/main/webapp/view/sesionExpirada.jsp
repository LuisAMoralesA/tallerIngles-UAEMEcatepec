<%@page import="java.sql.*" %>
<%@page import="com.mysql.jdbc.Driver" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="controller.*"%>
<%@page import = "jakarta.servlet.http.HttpSession"%>

<!doctype html>
<html lang = "es">
    
    <head>
        <meta charset = "UTF-8">
        <meta name = "viewport" content = "width-device-width, initial-scale = 1.0">
        <title> Pagina no encontrada </title>
        <link rel = "stylesheet" href = "/tallerDeInglesUAEM/css/style_login.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  
        <link href = "/tallerDeInglesUAEM/Images/uaem.png" rel = "icon"/>
        <!--Librerias para alertas emergentes-->
        <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js"></script>
        <!--Link para visualizar alertas
            https://sweetalert2.github.io/-->
    </head>

    <body class = "body-expired">
        <header class = "header">
            <div class="menu1 container">
                <img src="/tallerDeInglesUAEM/Images/Logo_Taller1.png" class = "logo-sesion" alt="" >

                <nav class = "navbar">
                    <ul>
                        <li>
                            
                        </li>
                    </ul>
                </nav>
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
                <img class ="img2" src="/tallerDeInglesUAEM/Images/sesionExpirada1.png" alt="alt"/>
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
                        <i class="fa-brands fa-square-instagram"></i>&nbsp; 
                        tallerdeinglesuaem@gmail.com
                    </a>         
                </li>
            </ul>
        </div>
    </footer>
        <script src = "/tallerDeInglesUAEM/js/prueba.js"></script>
    </body>
    
</html>